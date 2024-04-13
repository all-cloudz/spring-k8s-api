package com.k8s.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("k8s.control-plane")
public record KubeConfig(String host, String port, String user, String token) {

    public String getUrl() {
        return STR."https://\{host}:\{port}";
    }
}
