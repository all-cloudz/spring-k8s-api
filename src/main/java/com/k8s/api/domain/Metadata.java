package com.k8s.api.domain;

import java.util.Map;

public record Metadata(String name, String namespace, Map<String, String> labels, Map<String, String> annotations) {
}
