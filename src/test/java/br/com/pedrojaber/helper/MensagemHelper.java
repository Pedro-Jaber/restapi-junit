package br.com.pedrojaber.helper;

import br.com.pedrojaber.model.Mensagem;

public abstract class MensagemHelper {

    public static Mensagem gerarMensagem() {
        return Mensagem.builder()
                .usuario("João")
                .conteudo("conteudo")
                .build();
    }
}
