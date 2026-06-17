package bf.Sonabel.Ges_cli.Gestion.Log;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AppLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    @Enumerated(EnumType.STRING)
    private EntityName entityName;

    private String authorCode;

    private String codeObject;

    private String message;

    public AppLogs() {}

    public AppLogs(LocalDateTime dateTime, LogType logType, EntityName entityName, String authorCode, String codeObject, String message) {
        this.dateTime = dateTime;
        this.logType = logType;
        this.entityName = entityName;
        this.authorCode = authorCode;
        this.codeObject = codeObject;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public EntityName getEntityName() {
        return entityName;
    }

    public void setEntityName(EntityName entityName) {
        this.entityName = entityName;
    }

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }

    public String getCodeObject() {
        return codeObject;
    }

    public void setCodeObject(String codeObject) {
        this.codeObject = codeObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
