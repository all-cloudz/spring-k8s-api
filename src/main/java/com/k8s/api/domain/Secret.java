package com.k8s.api.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Map;
import java.util.stream.Collectors;

public record Secret(
        Metadata metadata,
        @JsonAlias("data") Map<String, String> encodedData,
        String type
) {

    public static final Decoder BASE64_DECODER = Base64.getDecoder();

    public Map<String, String> decodedData() {
        return encodedData.entrySet()
                          .stream()
                          .collect(Collectors.toMap(
                                  Map.Entry::getKey,
                                  entry -> new String(BASE64_DECODER.decode(entry.getValue()))
                          ));
    }
}
