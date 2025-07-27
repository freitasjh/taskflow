package br.com.systec.taskflow.integration.employee;

import br.com.systec.taskflow.api.v1.dto.LoginDTO;
import br.com.systec.taskflow.api.v1.dto.LoginResponseDTO;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.employee.api.model.Departament;
import br.com.systec.taskflow.employee.v1.dto.EmployeeInputDTO;
import br.com.systec.taskflow.integration.AbstractIT;
import br.com.systec.taskflow.integration.util.IntegrationTestUtil;
import br.com.systec.taskflow.integration.util.JsonUtil;
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

@Order(2)
@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerIT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(EmployeeControllerIT.class);
    private static final String ENDPOINT = RestPath.V1 + "/employees";
    private static final String ENDPOINT_LOGIN = RestPath.V1 + "/login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenInsertNewEmployee() throws Exception {
        log.info("@@@@ Cadastrando um novo funcionário @@@@");

        EmployeeInputDTO employee = new EmployeeInputDTO();
        employee.setName("John Doe");
        employee.setDepartament(Departament.DEV);
        employee.setUsername("johndoe");
        employee.setPassword("123456");
        employee.setEmail("teste@teste.com.br");
        employee.setCellphone("11999999999");
        employee.setPhone("1133333333");
        employee.setFederalId("123456789");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .content(JsonUtil.converteObjetoParaString(employee)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        EmployeeInputDTO employeeInputDTO = (EmployeeInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), EmployeeInputDTO.class);

        Assertions.assertThat(employeeInputDTO).isNotNull();
        IntegrationTestUtil.setEmployeeInputDTO(employeeInputDTO);
    }

    @Test
    @Order(2)
    void whenLoginApplicationIfNewEmployee() throws Exception {
        log.info("@@@@ Fazendo o login do admin @@@@");
        LoginDTO login = new LoginDTO();
        login.setUsername("johndoe");
        login.setPassword("123456");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(login)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        LoginResponseDTO response = (LoginResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), LoginResponseDTO.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getToken()).isNotNull();

        IntegrationTestUtil.setLoginResponseDTO(response);
        log.info("@@@@@@@@@ Token gerado {}", response.getToken());
    }

    @Test
    @Order(3)
    void whenFindByFilter() throws Exception {
        log.info("@@@@ Buscando funcionarios por filtro @@@@");

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ IntegrationTestUtil.getLoginResponseDTO().getToken())
                        .param("name", "John"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
