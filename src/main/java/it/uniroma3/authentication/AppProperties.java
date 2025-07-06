package it.uniroma3.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app")
public class AppProperties {
        /**
     * Directory sul filesystem in cui salvare gli upload (relativa al working dir dell'app).
     * Esempio: uploads/images
     */
    private String uploadDir;

    public String getUploadDir() { return uploadDir; }
    public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir; }
}
