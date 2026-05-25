package br.com.pedrojaber.service;

import br.com.pedrojaber.model.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MensagemService {

    Mensagem registrarMensagem(Mensagem mensagem);

    Mensagem obterMensagem(UUID id);

    Page<Mensagem> obterMensagens(Pageable pageable);

    Mensagem atualizarMensagem(UUID id, Mensagem mensagemAtulizada);

    boolean removerMensagem(UUID id);
}
