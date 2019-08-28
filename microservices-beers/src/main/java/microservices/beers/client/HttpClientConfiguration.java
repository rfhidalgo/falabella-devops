package microservices.beers.client;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(HttpClientConfiguration.PREFIX)
@Requires(property = HttpClientConfiguration.PREFIX)
public class HttpClientConfiguration {

    public static final String PREFIX = "bintray";
    public static final String BASE_URL = "http://apilayer.net/api/";
    public static final String ENDPOINT = "live";
    public static final String ACCESS_KEY = "4c93b425dfbbaf8a1b58068a1f4e13f2";
    public static final String MONEY_SOURCE = "USD";

    private String apiversion;

    private String organization;

    private String repository;

    private String username;

    private String token;

    public String getApiversion() {
        return apiversion;
    }

    public void setApiversion(String apiversion) {
        this.apiversion = apiversion;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("apiversion", getApiversion());
        m.put("organization", getOrganization());
        m.put("repository", getRepository());
        m.put("username", getUsername());
        m.put("token", getToken());
        return m;
    }
}