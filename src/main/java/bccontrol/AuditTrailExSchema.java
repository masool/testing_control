package bccontrol;

public class AuditTrailExSchema {

    private String remId;
    private String remStatus;
    private String message;

    public AuditTrailExSchema(){

    }

    public AuditTrailExSchema(String remId, String remStatus, String message) {
        this.remId = remId;
        this.remStatus = remStatus;
        this.message = message;
    }

    public String getRemId() {
        return remId;
    }

    public void setRemId(String remId) {
        this.remId = remId;
    }

    public String getRemStatus() {
        return remStatus;
    }

    public void setRemStatus(String remStatus) {
        this.remStatus = remStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
