package br.com.systec.taskflow.integration.kanban.controller;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
import br.com.systec.taskflow.kanban.v1.dto.KanbanInputDTO;
import org.junit.jupiter.api.Assertions;
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
public class KanbanBoardControllerV1IT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(KanbanBoardControllerV1IT.class);
    private static final String ENDPOINT = RestPath.V1 + "/kanban-boards";

    private static KanbanInputDTO kanbanBoard;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateKanbanBoard() throws Exception {
        kanbanBoard = new KanbanInputDTO();
        kanbanBoard.setName("Flowing_Kanban");
        kanbanBoard.setWorkflowId(IntegrationTestUtil.getWorkflow().getId());


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(kanbanBoard)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        KanbanInputDTO kanbanSaveResponse = (KanbanInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), KanbanInputDTO.class);

        Assertions.assertNotNull(kanbanSaveResponse);
        kanbanBoard = kanbanSaveResponse;
    }

    @Test
    @Order(2)
    void whenUpdateKanbanBoard() throws Exception {
        kanbanBoard.setName("Flowing_Kanban_atualizado");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(kanbanBoard)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        KanbanInputDTO kanbanSaveResponse = (KanbanInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), KanbanInputDTO.class);

        Assertions.assertNotNull(kanbanSaveResponse);
        Assertions.assertEquals(kanbanBoard.getName(), kanbanSaveResponse.getName());

        kanbanBoard = kanbanSaveResponse;
    }

    @Test
    @Order(3)
    void whenFindKanbanById() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/"+kanbanBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(kanbanBoard)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        KanbanInputDTO kanbanSaveResponse = (KanbanInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), KanbanInputDTO.class);

        Assertions.assertNotNull(kanbanSaveResponse);
        Assertions.assertEquals(kanbanBoard.getName(), kanbanSaveResponse.getName());
        Assertions.assertEquals(kanbanBoard.getId(), kanbanSaveResponse.getId());


        kanbanBoard = kanbanSaveResponse;
    }

    @Test
    @Order(4)
    void whenFindWorkflowByFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT +"/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                )
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
