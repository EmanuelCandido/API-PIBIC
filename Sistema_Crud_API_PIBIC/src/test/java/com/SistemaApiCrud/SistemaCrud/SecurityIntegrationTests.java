package com.SistemaApiCrud.SistemaCrud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTests {

    private static final String FRONT_ORIGIN = "http://localhost:5173";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveResponderPreflightCorsParaFrontLocal() throws Exception {
        mockMvc.perform(options("/auth/login")
                        .header(HttpHeaders.ORIGIN, FRONT_ORIGIN)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "content-type"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, FRONT_ORIGIN))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"));
    }

    @Test
    void deveAutenticarComJwtEAcessarRotaProtegida() throws Exception {
        Map<String, Object> json = login("admin", "admin123");
        String token = (String) json.get("token");

        assertThat(token).isNotBlank();

        mockMvc.perform(get("/usuarios")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void devePermitirAlunoAcessarSomenteProprioPerfil() throws Exception {
        Map<String, Object> json = login("aluno", "aluno123");
        String token = (String) json.get("token");
        Long idAluno = ((Number) json.get("idAluno")).longValue();

        mockMvc.perform(get("/alunos/" + idAluno + "/historico")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(get("/alunos/" + (idAluno + 999L) + "/historico")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deveCriarCasoNoProfessorAutenticadoEBloquearFiltroDeOutroProfessor() throws Exception {
        Map<String, Object> json = login("professor", "professor123");
        String token = (String) json.get("token");
        Long idProfessor = ((Number) json.get("idProfessor")).longValue();

        String respostaCaso = mockMvc.perform(post("/casos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titulo": "Caso de seguranca",
                                  "dificuldade": "MEDIA",
                                  "disciplina": "Clinica Medica",
                                  "areaSaude": "Medicina",
                                  "estilo": "Multipla escolha",
                                  "especialidade": "Pneumologia",
                                  "objetivoAprendizagem": "Testar acesso por dono",
                                  "nivelDificuldade": "MEDIA"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> caso = objectMapper.readValue(respostaCaso, new TypeReference<>() {});
        assertThat(((Number) caso.get("idProfessor")).longValue()).isEqualTo(idProfessor);

        mockMvc.perform(get("/casos?idProfessor=" + (idProfessor + 999L))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    private Map<String, Object> login(String username, String password) throws Exception {
        String respostaLogin = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "password": "%s"
                                }
                                """.formatted(username, password)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(respostaLogin, new TypeReference<>() {});
    }
}
