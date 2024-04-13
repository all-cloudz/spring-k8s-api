package com.k8s.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.k8s.api.config.KubeConfig;
import java.net.http.HttpClient;
import java.util.Optional;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class KubeService {

    private final RestClient kubeClient;

    @Autowired
    public KubeService(final KubeConfig kubeConfig, final SslBundles sslBundles) {
        final SSLContext sslContext = sslBundles.getBundle("k8s-client")
                                                .createSslContext();

        final HttpClient httpClient = HttpClient.newBuilder()
                                                .sslContext(sslContext)
                                                .build();

        this.kubeClient = RestClient.builder()
                                    .baseUrl(kubeConfig.getUrl())
                                    .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                                    .defaultHeader("Authorization", STR."Bearer \{kubeConfig.token()}")
                                    .build();
    }

    public Optional<JsonNode> getNodes() {
        return getNodes(JsonNode.class);
    }

    public <T> Optional<T> getNodes(final Class<T> clazz) {
        return Optional.ofNullable(
                kubeClient.get()
                        .uri("/api/v1/nodes")
                        .retrieve()
                        .body(clazz)
        );
    }

    public Optional<JsonNode> getPods(final String namespace) {
        return getPods(namespace, JsonNode.class);
    }

    public <T> Optional<T> getPods(final String namespace, final Class<T> clazz) {
        return Optional.ofNullable(
                kubeClient.get()
                          .uri(STR."/api/v1/namespaces/\{namespace}/pods")
                          .retrieve()
                          .body(clazz)
        );
    }
}
