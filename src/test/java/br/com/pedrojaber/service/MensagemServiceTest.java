package br.com.pedrojaber.service;

import br.com.pedrojaber.helper.MensagemHelper;
import br.com.pedrojaber.model.Mensagem;
import br.com.pedrojaber.repository.MensagemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class MensagemServiceTest {

    @Mock
    private MensagemRepository mensagemRepository;
    private MensagemService mensagemService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemService = new MensagemServiceImp(mensagemRepository);
    }

    @AfterEach
    void Teardown() throws Exception {
        mock.close();
    }

    @Nested
    class RegistrarMensagem {

        @Test
        void devePermitirRegistrarMensagem() {

            // Arrange
            var mensagem = MensagemHelper.gerarMensagem();

            when(mensagemRepository.save(any(Mensagem.class))).thenAnswer(
                    i -> i.getArgument(0)
            );

            // Act
            var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

            // Assert
            assertThat(mensagemRegistrada)
                    .isNotNull()
                    .isInstanceOf(Mensagem.class);

            assertThat(mensagemRegistrada.getId())
                    .isNotNull();

            assertThat(mensagemRegistrada.getUsuario())
                    .isEqualTo(mensagem.getUsuario());

            assertThat(mensagemRegistrada.getConteudo())
                    .isEqualTo(mensagem.getConteudo());

            verify(mensagemRepository, times(1)).save(mensagem);

        }

    }

    @Test
    void devePermitirObterMensagemPorId() {
        // Arrange
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);

        when(mensagemRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mensagem));

        // Act
        var mensagemObtida = mensagemService.obterMensagem(id);

        // Assert
        verify(mensagemRepository, times(1)).findById(id);
        assertThat(mensagemObtida).isEqualTo(mensagem);
    }

//    @Test
//    void devePermitirObterMensagens() {
//        fail("n implementado");
//    }
//
//    @Test
//    void devePermitirModificarMensagem() {
//        fail("n implementado");
//    }
//
//    @Test
//    void devePermitirRemoverMensagem() {
//        fail("n implementado");
//    }

}
