package bf.company.safety.service;

import bf.company.safety.dto.AcknowledgeAlertRequest;
import bf.company.safety.dto.CreateAlertRequest;
import bf.company.safety.dto.CreateCallRequest;
import bf.company.safety.dto.CreateEmployeeRequest;
import bf.company.safety.dto.CreateIncidentRequest;
import bf.company.safety.dto.RegisterDeviceRequest;
import bf.company.safety.model.Alert;
import bf.company.safety.model.CallRequest;
import bf.company.safety.model.DeviceToken;
import bf.company.safety.model.Employee;
import bf.company.safety.model.Incident;
import bf.company.safety.repository.AlertRepository;
import bf.company.safety.repository.CallRequestRepository;
import bf.company.safety.repository.DeviceTokenRepository;
import bf.company.safety.repository.EmployeeRepository;
import bf.company.safety.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SafetyService {
    public static final String ALL_ZONES = "TOUS";

    private final EmployeeRepository employeeRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final AlertRepository alertRepository;
    private final IncidentRepository incidentRepository;
    private final CallRequestRepository callRequestRepository;
    private final FcmNotificationService fcmNotificationService;

    public SafetyService(
            EmployeeRepository employeeRepository,
            DeviceTokenRepository deviceTokenRepository,
            AlertRepository alertRepository,
            IncidentRepository incidentRepository,
            CallRequestRepository callRequestRepository,
            FcmNotificationService fcmNotificationService
    ) {
        this.employeeRepository = employeeRepository;
        this.deviceTokenRepository = deviceTokenRepository;
        this.alertRepository = alertRepository;
        this.incidentRepository = incidentRepository;
        this.callRequestRepository = callRequestRepository;
        this.fcmNotificationService = fcmNotificationService;
    }

    public List<Employee> listEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFullName(request.fullName());
        employee.setPhone(request.phone());
        employee.setZone(request.zone());
        return employeeRepository.save(employee);
    }

    @Transactional
    public DeviceToken registerDevice(RegisterDeviceRequest request) {
        employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new NoSuchElementException("Employe introuvable"));

        DeviceToken deviceToken = deviceTokenRepository.findByToken(request.token())
                .orElseGet(DeviceToken::new);
        deviceToken.setEmployeeId(request.employeeId());
        deviceToken.setToken(request.token());
        deviceToken.setPlatform(request.platform());
        deviceToken.touch();
        return deviceTokenRepository.save(deviceToken);
    }

    public List<Alert> listAlerts(String zone) {
        if (zone == null || zone.isBlank()) {
            return alertRepository.findAllByOrderByCreatedAtDesc();
        }
        return alertRepository.findByTargetZoneIgnoreCaseOrTargetZoneIgnoreCaseOrderByCreatedAtDesc(zone, ALL_ZONES);
    }

    @Transactional
    public Alert createAlert(CreateAlertRequest request) {
        Alert alert = new Alert();
        alert.setTitle(request.title());
        alert.setMessage(request.message());
        alert.setLevel(request.level());
        alert.setTargetZone(request.targetZone());
        alert.setSender(request.sender());

        Alert savedAlert = alertRepository.save(alert);
        List<Long> employeeIds = employeesInTargetZone(request.targetZone()).stream()
                .map(Employee::getId)
                .toList();
        List<String> tokens = deviceTokenRepository.findByEmployeeIdIn(employeeIds).stream()
                .map(DeviceToken::getToken)
                .toList();
        fcmNotificationService.sendAlert(savedAlert, tokens);
        return savedAlert;
    }

    @Transactional
    public Alert acknowledge(Long alertId, AcknowledgeAlertRequest request) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new NoSuchElementException("Alerte introuvable"));
        employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new NoSuchElementException("Employe introuvable"));
        alert.getAcknowledgedByEmployeeIds().add(request.employeeId());
        return alertRepository.save(alert);
    }

    public List<Incident> listIncidents() {
        return incidentRepository.findAllByOrderByCreatedAtDesc();
    }

    public Incident createIncident(CreateIncidentRequest request) {
        employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new NoSuchElementException("Employe introuvable"));
        Incident incident = new Incident();
        incident.setEmployeeId(request.employeeId());
        incident.setZone(request.zone());
        incident.setDescription(request.description());
        return incidentRepository.save(incident);
    }

    public List<CallRequest> listCallRequests() {
        return callRequestRepository.findAllByOrderByCreatedAtDesc();
    }

    public CallRequest createCallRequest(CreateCallRequest request) {
        employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new NoSuchElementException("Employe introuvable"));
        CallRequest callRequest = new CallRequest();
        callRequest.setEmployeeId(request.employeeId());
        callRequest.setPhone(request.phone());
        callRequest.setZone(request.zone());
        callRequest.setUrgent(request.urgent());
        return callRequestRepository.save(callRequest);
    }

    private List<Employee> employeesInTargetZone(String targetZone) {
        List<Employee> employees = employeeRepository.findAll();
        if (ALL_ZONES.equalsIgnoreCase(targetZone)) {
            return employees;
        }
        return employees.stream()
                .filter(employee -> employee.getZone().equalsIgnoreCase(targetZone))
                .toList();
    }
}
