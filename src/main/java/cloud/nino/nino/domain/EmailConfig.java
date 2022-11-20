package cloud.nino.nino.domain;

public class EmailConfig {
    private String accessId;
    private String secretKey;
    private String from;
    private String to;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    @Override
    public String toString() {
        return "EmailConfig{" +
            "accessID='" + accessId + '\'' +
            ", secretKey='" + secretKey + '\'' +
            ", from='" + from + '\'' +
            ", to='" + to + '\'' +
            '}';
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
