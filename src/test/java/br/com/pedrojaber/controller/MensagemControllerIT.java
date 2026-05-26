package br.com.pedrojaber.controller;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static br.com.pedrojaber.helper.MensagemHelper.gerarMensagem;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MensagemControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    void devePermitirRegistrarMensagem() {
        var mensagemRequest = gerarMensagem();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mensagemRequest)
                .when()
                .post("/mensagens")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("usuario"))
                .body("$", hasKey("conteudo"))
                .body("$", hasKey("gostei"))
                .body("$", hasKey("dataCriacao"))
                .body("usuario", equalTo(mensagemRequest.getUsuario()))
                .body("conteudo", equalTo(mensagemRequest.getConteudo()))
                .body("gostei", equalTo(mensagemRequest.getGostei()));
    }

    @Test
    void devePermitirObterMensagem() {
        // id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'
        // uduario = 'alice'
        // conteudo = 'Olá, tudo bem?'
        // dataCriacao = '2024-01-15 08:30:00'
        // dataAlteracao = '2024-01-15 08:30:00'
        // gostei =  0
        String id = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";
        String usuario = "alice";
        String conteudo = "Olá, tudo bem?";
        int gostei = 0;

        when()
                .get("/mensagens/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("usuario"))
                .body("$", hasKey("conteudo"))
                .body("$", hasKey("gostei"))
                .body("$", hasKey("dataCriacao"))
                .body("usuario", equalTo(usuario))
                .body("gostei", equalTo(gostei));
    }
}
