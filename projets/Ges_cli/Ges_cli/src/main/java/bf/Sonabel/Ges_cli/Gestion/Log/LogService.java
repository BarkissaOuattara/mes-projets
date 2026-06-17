package bf.Sonabel.Ges_cli.Gestion.Log;


import java.util.List;

public interface LogService {
    AppLogs createLog(AppLogs log);
    List<AppLogs> getAllLogs();
}
