package com.k8s.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.k8s.api.service.KubeService;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/k8s")
@RequiredArgsConstructor
@Slf4j
public class KubeController {

    private final KubeService kubeService;

    @GetMapping("/nodes/names")
    public List<String> findNodeNames() {
        final JsonNode items = kubeService.getNodes()
                                          .orElseThrow()
                                          .get("items");

        return StreamSupport.stream(items.spliterator(), false)
                            .map(item -> item.get("metadata").get("name").asText())
                            .toList();
    }

    @GetMapping("/{namespace}/pods/names")
    public List<String> findPodNames(@PathVariable String namespace) {
        final JsonNode items = kubeService.getPods(namespace)
                                          .orElseThrow()
                                          .get("items");

        return StreamSupport.stream(items.spliterator(), false)
                            .map(item -> item.get("metadata").get("name").asText())
                            .toList();
    }
}
