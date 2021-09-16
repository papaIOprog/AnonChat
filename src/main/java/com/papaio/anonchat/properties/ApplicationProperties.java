package com.papaio.anonchat.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.papaio.anonchat")
public class ApplicationProperties {

    private String systemName = "System";


    private String userPrefix = "/user";

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemname) {
        this.systemName = systemname;
    }

    public String getUserPrefix() {
        return userPrefix;
    }

    public void setUserPrefix(String userPrefix) {
        this.userPrefix = userPrefix;
    }
}