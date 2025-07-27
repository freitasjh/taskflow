package br.com.systec.taskflow.integration.team;

import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
import br.com.systec.taskflow.team.v1.dto.EmployeeDTO;
import br.com.systec.taskflow.team.v1.dto.TeamInputDTO;
import br.com.systec.taskflow.team.v1.dto.TeamMembersInputDTO;
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
public class TeamControllerV1IT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(TeamControllerV1IT.class);
    private static final String ENDPOINT = RestPath.V1 + "/teams";

    private static TeamInputDTO teamInput;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateTeam() throws Exception {
        teamInput = new TeamInputDTO();
        teamInput.setDescription("Equipe de Teste");
        teamInput.setName("Equipe Teste");
        teamInput.setPrefix("EQT");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(teamInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TeamInputDTO teamSavedResponse = (TeamInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), TeamInputDTO.class);

        Assertions.assertThat(teamSavedResponse).isNotNull();
        Assertions.assertThat(teamSavedResponse.getId()).isNotNull();

        teamInput = teamSavedResponse;
    }

    @Test
    @Order(2)
    void whenAddNewMemberIntoTeam() throws Exception {
        log.info("@@@@ Adicionando um novo membro à equipe @@@@");

        Long teamId = teamInput.getId();
        Long employeeId = IntegrationTestUtil.getEmployeeInputDTO().getId();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/members/{teamId}/{employeeId}", teamId, employeeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(3)
    void whenDeleteMemberOfTeam() throws Exception {
        log.info("@@@@ Removendo membro da equipe @@@@");

        Long teamId = teamInput.getId();
        Long employeeId = IntegrationTestUtil.getEmployeeInputDTO().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/members/{teamId}/{employeeId}", teamId, employeeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(4)
    void whenUpdateTeamAndAddANewMember() throws Exception {
        log.info("@@@@ Atualizando a equipe e adicionando um novo membro @@@@");

        teamInput.setName("Equipe Teste Atualizada");
        TeamMembersInputDTO teamMember = new TeamMembersInputDTO();

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(IntegrationTestUtil.getEmployeeInputDTO().getId());

        teamMember.setEmployee(employeeDTO);
        teamInput.getMembers().add(teamMember);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(teamInput)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TeamInputDTO teamSavedResponse = (TeamInputDTO) JsonUtil.convertStringToObject(result.getResponse()
                .getContentAsString(), TeamInputDTO.class);

        Assertions.assertThat(teamSavedResponse).isNotNull();
        Assertions.assertThat(teamSavedResponse.getId()).isNotNull();
        Assertions.assertThat(teamSavedResponse.getName()).isEqualTo("Equipe Teste Atualizada");
        Assertions.assertThat(teamSavedResponse.getMembers()).isNotEmpty();

        IntegrationTestUtil.setTeam(teamSavedResponse);
    }

    @Test
    @Order(5)
    void whenFindByFilter() throws Exception {
        log.info("@@@@ Realizando a pesquisa de equipes com filtro @@@@");

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+ "/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
