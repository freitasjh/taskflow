package br.com.systec.taskflow.integration.workflow.controller;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
import br.com.systec.taskflow.task.status.v1.dto.StatusInputDTO;
import br.com.systec.taskflow.task.status.v1.dto.WorkflowInputDTO;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkflowControllerV1IT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(WorkflowControllerV1IT.class);
    private static final String ENDPOINT = RestPath.V1 + "/workflows";
    private static WorkflowInputDTO workflow;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    void whenCreateWorkflow() throws Exception {
        log.info("@@@ Criando um novo workflow @@@");

        workflow = new WorkflowInputDTO();
        workflow.setName("Workflow Teste");
        workflow.setListOfStatus(listStatusToInsert());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(workflow)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        WorkflowInputDTO workflowSaveResponse = (WorkflowInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), WorkflowInputDTO.class);

        Assertions.assertNotNull(workflowSaveResponse);
        Assertions.assertNotNull(workflowSaveResponse.getId());
        Assertions.assertNotNull(workflowSaveResponse.getListOfStatus());
        Assertions.assertFalse(workflowSaveResponse.getListOfStatus().isEmpty());

        workflow = workflowSaveResponse;
    }

    @Test
    @Order(2)
    void whenAddNewStatusToWorkflow() throws Exception {
        StatusInputDTO statusInputDTO = new StatusInputDTO();
        statusInputDTO.setName("Em analise");
        statusInputDTO.setOrder(2);
        statusInputDTO.setDescription("Analisar a tarefar para definir os prazos");

        workflow.getListOfStatus().add(statusInputDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(workflow)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        WorkflowInputDTO workflowSaveResponse = (WorkflowInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), WorkflowInputDTO.class);

        Assertions.assertNotNull(workflowSaveResponse);
        Assertions.assertNotNull(workflowSaveResponse.getId());
        Assertions.assertNotNull(workflowSaveResponse.getListOfStatus());
        Assertions.assertFalse(workflowSaveResponse.getListOfStatus().isEmpty());
        Assertions.assertEquals(2, workflowSaveResponse.getListOfStatus().size());

        workflow = workflowSaveResponse;
    }

    @Test
    @Order(3)
    void whenRemoveStatusWorkflow() throws Exception {
        workflow.getListOfStatus().remove(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(workflow)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        WorkflowInputDTO workflowSaveResponse = (WorkflowInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), WorkflowInputDTO.class);

        Assertions.assertNotNull(workflowSaveResponse);
        Assertions.assertNotNull(workflowSaveResponse.getId());
        Assertions.assertNotNull(workflowSaveResponse.getListOfStatus());
        Assertions.assertFalse(workflowSaveResponse.getListOfStatus().isEmpty());
        Assertions.assertEquals(1, workflowSaveResponse.getListOfStatus().size());

        workflow = workflowSaveResponse;
    }

    @Test
    @Order(4)
    void whenFindWorkflowById() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT +"/"+workflow.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + IntegrationTestUtil.getLoginResponseDTO().getToken())
                )
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        WorkflowInputDTO workflowSaveResponse = (WorkflowInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), WorkflowInputDTO.class);

        Assertions.assertNotNull(workflowSaveResponse);
        Assertions.assertNotNull(workflowSaveResponse.getId());
        Assertions.assertNotNull(workflowSaveResponse.getListOfStatus());
        Assertions.assertFalse(workflowSaveResponse.getListOfStatus().isEmpty());
        Assertions.assertEquals(1, workflowSaveResponse.getListOfStatus().size());

        IntegrationTestUtil.setWorkflow(workflowSaveResponse);
    }

    @Test
    @Order(5)
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


    private List<StatusInputDTO> listStatusToInsert() {
        StatusInputDTO statusInputDTO = new StatusInputDTO();
        statusInputDTO.setName("Backlog");
        statusInputDTO.setDescription("Status de inicio");
        statusInputDTO.setOrder(1);

        List<StatusInputDTO> listOfStatus = new ArrayList<>();
        listOfStatus.add(statusInputDTO);

        return listOfStatus;
    }
}
