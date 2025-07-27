package br.com.systec.taskflow.integration.workflow.status.controller;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
import br.com.systec.taskflow.task.status.v1.dto.StatusInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatusControllerV1IT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(StatusControllerV1IT.class);
    private static final String ENDPOINT = RestPath.V1 + "/workflow-status";

    private static StatusInputDTO statusInput;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenSaveNewStatus() throws Exception {
        statusInput = new StatusInputDTO();
        statusInput.setName("backlog");
        statusInput.setDescription("backlog");
        statusInput.setOrder(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(statusInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        StatusInputDTO statusResponse = (StatusInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), StatusInputDTO.class);

        Assertions.assertThat(statusResponse).isNotNull();
        Assertions.assertThat(statusResponse.getId()).isNotNull();

        statusInput = statusResponse;
    }

    @Test
    @Order(2)
    void whenUpdateNewStatus() throws Exception {
        statusInput.setName("backlog");
        statusInput.setDescription("backlog atualizado");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(statusInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        StatusInputDTO statusResponse = (StatusInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), StatusInputDTO.class);

        Assertions.assertThat(statusResponse).isNotNull();
        Assertions.assertThat(statusResponse.getDescription()).isEqualTo(statusInput.getDescription());

        statusInput = statusResponse;
    }

    @Test
    @Order(3)
    void whenFindAllStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(statusInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
