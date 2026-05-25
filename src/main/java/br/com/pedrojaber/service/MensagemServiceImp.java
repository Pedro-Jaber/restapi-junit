package br.com.pedrojaber.service;

import br.com.pedrojaber.exception.MensagemNotFoundException;
import br.com.pedrojaber.model.Mensagem;
import br.com.pedrojaber.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensagemServiceImp implements MensagemService {

    private final MensagemRepository mensagemRepository;

    @Override
    public Mensagem registrarMensagem(Mensagem mensagem) {
        mensagem.setId(UUID.randomUUID());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem obterMensagem(UUID id) {
        return mensagemRepository.findById(id)
                .orElseThrow( () -> new MensagemNotFoundException("Mensagem Not Found"));
    }

    @Override
    public Page<Mensagem> obterMensagens(Pageable pageable) {
        return mensagemRepository.findAll(pageable);
    }

    @Override
    public Mensagem atualizarMensagem(UUID id, Mensagem mensagemAtulizada) {
        var mensagem = obterMensagem(id);
        if (!mensagem.getId().equals(mensagemAtulizada.getId())) {
            throw new MensagemNotFoundException("mensagem não encontrada");
        }

        mensagem.setDataAlteracao(LocalDateTime.now());
        mensagem.setConteudo(mensagemAtulizada.getConteudo());

        return mensagemRepository.save(mensagem);
    }


    @Override
    public boolean removerMensagem(UUID id) {
        var mensagem = obterMensagem(id);
        mensagemRepository.delete(mensagem);
        return true;
    }
}
