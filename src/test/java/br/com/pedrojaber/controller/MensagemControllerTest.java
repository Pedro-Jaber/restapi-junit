package br.com.pedrojaber.controller;

import br.com.pedrojaber.handler.GlobalExceptionHandler;
import br.com.pedrojaber.model.Mensagem;
import br.com.pedrojaber.repository.MensagemRepository;
import br.com.pedrojaber.service.MensagemService;
import br.com.pedrojaber.service.MensagemServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.pedrojaber.helper.MensagemHelper.gerarMensagem;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MensagemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MensagemService mensagemService;
    private MensagemController mensagemController;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemController = new MensagemController(mensagemService);
        mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void Teardown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirRegistrarMensagem() throws Exception {
        // Arrange
        var mensagemRequest = gerarMensagem();

        when(mensagemService.registrarMensagem(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act + Assert
        mockMvc.perform(post("/mensagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mensagemRequest))
                )
                .andExpect(status().isCreated());

        verify(mensagemService, times(1)).registrarMensagem(any(Mensagem.class));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
