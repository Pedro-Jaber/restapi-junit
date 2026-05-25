package br.com.pedrojaber.service;

import br.com.pedrojaber.model.Mensagem;

import java.util.UUID;

public interface MensagemService {

    Mensagem registrarMensagem(Mensagem mensagem);

    Mensagem obterMensagem(UUID id);
}
