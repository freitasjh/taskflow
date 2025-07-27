package br.com.systec.taskflow.integration.task.controller;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.task.api.model.TaskPriority;
import br.com.systec.taskflow.task.api.model.TaskStatus;
import br.com.systec.taskflow.task.v1.dto.TaskInputDTO;
import br.com.systec.taskflow.task.v1.dto.TaskInputObservationDTO;
import br.com.systec.taskflow.task.v1.dto.TaskCommentDTO;
import br.com.systec.taskflow.task.v1.dto.TaskResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerV1IT extends AbstractIT {
    private static Logger log = LoggerFactory.getLogger(TaskControllerV1IT.class);
    private static final String ENDPOINT = RestPath.V1+"/tasks";

    @Autowired
    private MockMvc mockMvc;
    private static TaskInputDTO task;
    private static TaskCommentDTO comment;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void whenCreateTask_thenReturnCode406() throws Exception{
        log.info("@@@@ Criando uma nova tarefa sem criticidade informada, e retorna erro 406 @@@@");
        task = new TaskInputDTO();
        task.setCreatedBy(IntegrationTestUtil.getEmployeeInputDTO().getId());
        task.setDateCreate(LocalDateTime.now());
        task.setTeamId(IntegrationTestUtil.getTeam().getId());
        task.setDescription("Tarefa de teste");
        task.setStatus("OPEN");

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(2)
    void whenCreateTask() throws Exception{
        log.info("@@@ Criando uma nova tarefa e retornando codigo 200 @@@");
        task = new TaskInputDTO();
        task.setCreatedBy(IntegrationTestUtil.getEmployeeInputDTO().getId());
        task.setDateCreate(LocalDateTime.now());
        task.setTeamId(IntegrationTestUtil.getTeam().getId());
        task.setDescription("Tarefa de teste");
        task.setStatus("OPEN");
        task.setPriority(TaskPriority.HIGH);
        task.setProjectId(IntegrationTestUtil.getProject().getId());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TaskInputDTO taskSaveResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TaskInputDTO.class);

        assertThat(taskSaveResponse).isNotNull();
        assertThat(taskSaveResponse.getCode().contains("null")).isFalse();
        assertThat(taskSaveResponse.getProjectId()).isNotNull();
        assertThat(taskSaveResponse.getWorkflowStatusId()).isNotNull();
        assertThat(taskSaveResponse.getStatus()).isNotNull();

        task = taskSaveResponse;
    }

    @Test
    @Order(3)
    void whenAddAccountable() throws Exception{
        log.info("@@@ Adiciona o responsavel da tarefa @@@");
        String endpoint = ENDPOINT+"/"+task.getId()+"/accountable/add/"+IntegrationTestUtil.getEmployeeInputDTO().getId();

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(4)
    void whenFindById() throws Exception {
        log.info("@@@ Pesquisa os dados da tarefa pelo id @@@");

        TaskInputDTO taskResponse = findTaskById();

        assertThat(taskResponse).isNotNull();
        assertThat(taskResponse.getCode().contains("null")).isFalse();
        assertThat(taskResponse.getProjectId()).isNotNull();
        assertThat(taskResponse.getWorkflowStatusId()).isNotNull();
        assertThat(taskResponse.getStatus()).isNotNull();
        assertThat(taskResponse.getAccountable()).isNotNull();
        assertThat(taskResponse.getAccountable()).isEqualTo(IntegrationTestUtil.getEmployeeInputDTO().getId());

        task = taskResponse;
    }

    @Test
    @Order(5)
    void whenFindPageFilterTasks() throws Exception {
        log.info("@@@ Retorna as tarefas cadastradas @@@");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/filters")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode content = root.get("content");

        List<TaskResponseDTO> taskResult = objectMapper.readValue(
                content.toPrettyString(), new TypeReference<List<TaskResponseDTO>>() {}
        );

        assertThat(taskResult).isNotNull();
        assertThat(taskResult).isNotEmpty();
        assertThat(taskResult.get(0).getAccountableName()).isEqualTo(IntegrationTestUtil.getEmployeeInputDTO().getName());
        assertThat(taskResult.get(0).getWorkflowStatusName()).isNotNull();
    }

    @Test
    @Order(6)
    void whenCreateNewComment() throws Exception {
        log.info("@@@ Adiciona um novo comentario na tarefa @@@");

        TaskInputObservationDTO taskObservationDTO = new TaskInputObservationDTO();
        taskObservationDTO.setContent("Observação 01 teste");
        String endpoint = ENDPOINT + "/"+task.getId()+"/comments/add";

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(objectMapper.writeValueAsString(taskObservationDTO)))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TaskInputDTO taskResponse = findTaskById();

        assertThat(taskResponse.getListComments().size()).isEqualTo(2);
        assertThat(taskResponse.getListComments().get(1).getContent()).isEqualTo(taskObservationDTO.getContent());
        comment = taskResponse.getListComments().get(1);
    }

    @Test
    @Order(7)
    void whenUpdateComment() throws Exception {
        log.info("@@@ Atualiza o comentario da tarefa @@@");

        TaskInputObservationDTO taskObservationDTO = new TaskInputObservationDTO();
        taskObservationDTO.setContent("Observação 01 teste atualizado");

        String endpoint = ENDPOINT + "/"+task.getId()+"/comments/update/"+comment.getId();

        mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(objectMapper.writeValueAsString(taskObservationDTO)))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TaskInputDTO taskResponse = findTaskById();
        assertThat(taskResponse.getListComments().size()).isEqualTo(2);
        assertThat(taskResponse.getListComments().get(1).getContent()).isEqualTo(taskObservationDTO.getContent());
    }

    @Test
    @Order(8)
    void whenRemoveComment() throws Exception {
        log.info("@@@ Remove o comentario da tarefa @@@");

        String endpoint = ENDPOINT + "/"+task.getId()+"/comments/remove/"+comment.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TaskInputDTO taskResponse = findTaskById();
        assertThat(taskResponse.getListComments().size()).isEqualTo(1);
    }

    @Test
    @Order(9)
    void whenCancelTaskAndComment() throws Exception {
        log.info("@@@ Cancela a tarefa @@@");

        String endpoint = ENDPOINT + "/"+task.getId()+"/cancel";
        TaskCommentDTO taskComment = new TaskCommentDTO();
        taskComment.setContent("Cancelando tarefa");

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(objectMapper.writeValueAsString(taskComment)))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TaskInputDTO taskResponse = findTaskById();
        assertThat(taskResponse.getStatus()).isEqualTo(TaskStatus.CANCELED.name());
    }

    private TaskInputDTO findTaskById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/"+task.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), TaskInputDTO.class);
    }

}
