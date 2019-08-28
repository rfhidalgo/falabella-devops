package microservices.beers.client;

import java.util.Map;

public class HttpClientApiLayerEntity {

    boolean success;
    String terms;
    String privacy;
    Long timestamp;
    String source;
    Map<String, Object> error;
    Map<String, Object> quotes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }

    public Map<String, Object> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Object> quotes) {
        this.quotes = quotes;
    }

}
