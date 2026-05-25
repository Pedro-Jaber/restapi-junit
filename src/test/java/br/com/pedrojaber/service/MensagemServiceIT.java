package br.com.pedrojaber.service;

import br.com.pedrojaber.helper.MensagemHelper;
import br.com.pedrojaber.model.Mensagem;
import br.com.pedrojaber.repository.MensagemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MensagemServiceIT {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private MensagemService mensagemService;

    @Test
    void devePermitirRegistrarMensagem() {

        // Arrange
        var mensagem = MensagemHelper.gerarMensagem();

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
    }

    @Test
    void devePermitirObterMensagem() {

        // Arrange
        var mensagem = MensagemHelper.gerarMensagem();
        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        // Act
        var mensagemObtida = mensagemService.obterMensagem(mensagemRegistrada.getId());

        // Assert
        assertThat(mensagemObtida)
                .isInstanceOf(Mensagem.class)
                .isNotNull();

        assertThat(mensagemObtida.getId())
                .isNotNull();

        assertThat(mensagemObtida.getUsuario())
                .isNotNull();

        assertThat(mensagemObtida.getConteudo())
                .isNotNull();
    }

    @Test
    void devePermitirRemoverMensagem() {

        // Arrange
        var mensagem = MensagemHelper.gerarMensagem();
        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        // Act
        var mensagemRemovida = mensagemService.obterMensagem(mensagemRegistrada.getId());

        // Assert
        assertThat(mensagemRemovida).isTrue();

    }
}
