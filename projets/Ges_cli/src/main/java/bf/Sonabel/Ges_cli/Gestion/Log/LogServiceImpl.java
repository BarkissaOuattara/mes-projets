package bf.Sonabel.Ges_cli.Gestion.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository appLogsRepository;

    @Override
    public AppLogs createLog(AppLogs log) {
        log.setDateTime(LocalDateTime.now());
        return appLogsRepository.save(log);
    }

    @Override
    public List<AppLogs> getAllLogs() {
        return appLogsRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
    }
}
