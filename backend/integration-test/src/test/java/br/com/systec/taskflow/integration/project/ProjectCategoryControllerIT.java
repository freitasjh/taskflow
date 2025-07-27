package br.com.systec.taskflow.integration.project;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
import br.com.systec.taskflow.project.v1.dto.ProjectCategoryInputDTO;
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

@Order(4)
@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectCategoryControllerIT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(ProjectCategoryControllerIT.class);
    private static final String ENDPOINT = RestPath.V1 + "/project-categories";

    @Autowired
    private MockMvc mockMvc;
    private static ProjectCategoryInputDTO inputDTO;

    @Test
    @Order(1)
    void whenInsertNewProjectStatus() throws Exception {
        log.info("@@@ Cadastrado uma nova Categoria de projeto @@@");
        inputDTO = new ProjectCategoryInputDTO();
        inputDTO.setDescription("Categoria teste de cadastro");
        inputDTO.setName("Categoria teste");


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ProjectCategoryInputDTO projectStatusSaveResponse = (ProjectCategoryInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), ProjectCategoryInputDTO.class);

        Assertions.assertThat(projectStatusSaveResponse).isNotNull();
        inputDTO = projectStatusSaveResponse;
    }

    @Test
    @Order(2)
    void whenUpdateProjectStatusName() throws Exception {
        log.info("@@@ Atualizando a categoria de projeto @@@");

        String oldStatusName = inputDTO.getName();

        inputDTO.setName("Categoria atualizado");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ProjectCategoryInputDTO projectStatusSaveResponse = (ProjectCategoryInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), ProjectCategoryInputDTO.class);

        Assertions.assertThat(projectStatusSaveResponse).isNotNull();
        Assertions.assertThat(projectStatusSaveResponse.getName()).isNotEqualTo(oldStatusName);

        IntegrationTestUtil.setProjectCategory(projectStatusSaveResponse);
    }

    @Test
    @Order(3)
    void whenFindCategoryById() throws Exception{
        log.info("@@@ Pesquisa Categoria de projeto pelo id @@@");
        Long id = inputDTO.getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ProjectCategoryInputDTO projectStatusSaveResponse = (ProjectCategoryInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), ProjectCategoryInputDTO.class);

        Assertions.assertThat(projectStatusSaveResponse).isNotNull();
        Assertions.assertThat(projectStatusSaveResponse.getId()).isEqualTo(inputDTO.getId());
        Assertions.assertThat(projectStatusSaveResponse.getName()).isEqualTo(inputDTO.getName());
    }

    @Test
    @Order(4)
    void whenFindCategoryByFilter() throws Exception {
        String endpointFilter = ENDPOINT +"/filter";

        mockMvc.perform(MockMvcRequestBuilders.get(endpointFilter)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(1))
                .andReturn();
    }

    @Test
    @Order(5)
    void whenFindCategoryByFilter_thenNotReturnValue() throws Exception {
        String endpointFilter = ENDPOINT +"/filter?keyword=iniciando";

        mockMvc.perform(MockMvcRequestBuilders.get(endpointFilter)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(0))
                .andReturn();
    }
}
