package com.k8s.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = { @JsonCreator })
public class Secrets {

    @JsonProperty("items")
    private final List<Secret> collection;

    public Secret getFirst() {
        return collection.getFirst();
    }
}
