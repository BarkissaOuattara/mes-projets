package bf.company.safety.controller;

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
import bf.company.safety.service.SafetyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SafetyController {
    private final SafetyService safetyService;

    public SafetyController(SafetyService safetyService) {
        this.safetyService = safetyService;
    }

    @GetMapping("/employees")
    public List<Employee> listEmployees() {
        return safetyService.listEmployees();
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return safetyService.createEmployee(request);
    }

    @PostMapping("/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceToken registerDevice(@Valid @RequestBody RegisterDeviceRequest request) {
        return safetyService.registerDevice(request);
    }

    @GetMapping("/alerts")
    public List<Alert> listAlerts(@RequestParam(required = false) String zone) {
        return safetyService.listAlerts(zone);
    }

    @PostMapping("/alerts")
    @ResponseStatus(HttpStatus.CREATED)
    public Alert createAlert(@Valid @RequestBody CreateAlertRequest request) {
        return safetyService.createAlert(request);
    }

    @PatchMapping("/alerts/{id}/acknowledge")
    public Alert acknowledge(@PathVariable Long id, @Valid @RequestBody AcknowledgeAlertRequest request) {
        return safetyService.acknowledge(id, request);
    }

    @GetMapping("/incidents")
    public List<Incident> listIncidents() {
        return safetyService.listIncidents();
    }

    @PostMapping("/incidents")
    @ResponseStatus(HttpStatus.CREATED)
    public Incident createIncident(@Valid @RequestBody CreateIncidentRequest request) {
        return safetyService.createIncident(request);
    }

    @GetMapping("/call-requests")
    public List<CallRequest> listCallRequests() {
        return safetyService.listCallRequests();
    }

    @PostMapping("/call-requests")
    @ResponseStatus(HttpStatus.CREATED)
    public CallRequest createCallRequest(@Valid @RequestBody CreateCallRequest request) {
        return safetyService.createCallRequest(request);
    }
}
