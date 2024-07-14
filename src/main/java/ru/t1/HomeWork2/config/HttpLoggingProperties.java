package ru.t1.HomeWork2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "logging")
public class HttpLoggingProperties {
    private Boolean enabled;
}