package br.com.pedrojaber.service;

import br.com.pedrojaber.helper.MensagemHelper;
import br.com.pedrojaber.model.Mensagem;
import br.com.pedrojaber.repository.MensagemRepository;
import jakarta.transaction.Transactional;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MensagemServiceIT {

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
        var id = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

        // Act
        var mensagemObtida = mensagemService.obterMensagem(id);

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
        var id = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f12345678901");

        // Act
        boolean mensagemRemovida = mensagemService.removerMensagem(id);

        // Assert
        assertThat(mensagemRemovida).isEqualTo(true);

    }
}
