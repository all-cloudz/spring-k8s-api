package com.k8s.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k8s.api.service.KubeService;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class KubeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KubeController kubeController;

    @MockBean
    private KubeService kubeService;

    @Test
    @DisplayName("노드 이름 조회 성공")
    void findNodeNames() {
        // given, when
        try (FileInputStream input = new FileInputStream("test_data/node_infos.json")) {
            Mockito.when(kubeService.getNodes())
                   .thenReturn(Optional.of(
                           objectMapper.readValue(input, JsonNode.class)
                   ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        final List<String> actual = kubeController.findNodeNames();
        assertThat(actual).isEqualTo(List.of("test-node-1", "test-node-2"));
    }

    @Test
    @DisplayName("파드 이름 조회 성공")
    void findPodNames() {
        // given, when
        try (FileInputStream input = new FileInputStream("test_data/pod_infos.json")) {
            Mockito.when(kubeService.getPods("test-namespace"))
                   .thenReturn(Optional.of(
                           objectMapper.readValue(input, JsonNode.class)
                   ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        final List<String> actual = kubeController.findPodNames("test-namespace");
        assertThat(actual).isEqualTo(List.of("test-pod-1", "test-pod-2"));
    }
}