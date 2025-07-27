package br.com.systec.taskflow.integration.project;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.RabbitMQContainerIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
import br.com.systec.taskflow.project.v1.dto.ProjectInputDTO;
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
public class ProjectControllerV1IT extends AbstractIT implements RabbitMQContainerIT {
    private static final Logger log = LoggerFactory.getLogger(ProjectControllerV1IT.class);
    private static final String ENDPOINT = RestPath.V1+ "/projects";

    private static ProjectInputDTO projectInput;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreatingProjectWithoutTeamPrincipal_thenReturnValidationError() throws Exception {
        projectInput = new ProjectInputDTO();
        projectInput.setDescription("Projeto Teste");
        projectInput.setName("Projeto Teste");
        projectInput.setStatus(IntegrationTestUtil.getProjectStatus());
        projectInput.setCategory(IntegrationTestUtil.getProjectCategory());
        projectInput.setPrefix("FGT");

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(projectInput)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].fieldName").value("teamId"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(2)
    void whenCreateProject() throws Exception {
        log.info("@@@ Cadastrado um novo Projeto @@@");

        projectInput = new ProjectInputDTO();
        projectInput.setDescription("Projeto Teste");
        projectInput.setName("Projeto Teste");
        projectInput.setStatus(IntegrationTestUtil.getProjectStatus());
        projectInput.setCategory(IntegrationTestUtil.getProjectCategory());
        projectInput.setTeamId(IntegrationTestUtil.getTeam().getId());
        projectInput.setTeamName(IntegrationTestUtil.getTeam().getName());
        projectInput.setPrefix("FGT");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(projectInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ProjectInputDTO projectSaveResponse = (ProjectInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), ProjectInputDTO.class);

        Assertions.assertThat(projectSaveResponse).isNotNull();
        Assertions.assertThat(projectSaveResponse.getId()).isNotNull();
        Assertions.assertThat(projectSaveResponse.getTeamId()).isNotNull();
        Assertions.assertThat(projectSaveResponse.getPrefix()).isNotNull();

        projectInput = projectSaveResponse;
        IntegrationTestUtil.setProject(projectInput);
        Thread.sleep(1000); // Aguardar o RabbitMQ processar a mensagem
    }

    @Test
    @Order(3)
    void whenUpdateNameProject() throws Exception {
        log.info("@@@ Atualizando o nome do Projeto @@@");

        String oldProjectName = projectInput.getName();
        projectInput.setName("Projeto Teste Atualizado");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(projectInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ProjectInputDTO projectUpdateResponse = (ProjectInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), ProjectInputDTO.class);

        Assertions.assertThat(projectUpdateResponse).isNotNull();
        Assertions.assertThat(projectUpdateResponse.getId()).isNotNull();
        Assertions.assertThat(projectUpdateResponse.getName()).isNotEqualTo(oldProjectName);

        projectInput = projectUpdateResponse;
    }

    @Test
    @Order(4)
    void whenFindProjectById() throws Exception{
        log.info("@@@ Pesquisand Status de projeto pelo id @@@");
        Long id = projectInput.getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ProjectInputDTO projectStatusSaveResponse = (ProjectInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), ProjectInputDTO.class);

        Assertions.assertThat(projectStatusSaveResponse).isNotNull();
        Assertions.assertThat(projectStatusSaveResponse.getId()).isEqualTo(projectInput.getId());
        Assertions.assertThat(projectStatusSaveResponse.getName()).isEqualTo(projectInput.getName());
    }
}
