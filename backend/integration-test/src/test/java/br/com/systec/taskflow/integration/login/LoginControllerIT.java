package br.com.systec.taskflow.integration.login;

import br.com.systec.taskflow.api.v1.dto.LoginDTO;
import br.com.systec.taskflow.api.v1.dto.LoginResponseDTO;
import br.com.systec.taskflow.commons.controller.RestPath;
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

@Order(1)
@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerIT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(LoginControllerIT.class);
    private static final String ENDPOINT = RestPath.V1 + "/login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenLoginApplication() throws Exception {
        log.info("@@@@ Fazendo o login do admin @@@@");
        LoginDTO login = new LoginDTO();
        login.setUsername("admin");
        login.setPassword("admin");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
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
}
