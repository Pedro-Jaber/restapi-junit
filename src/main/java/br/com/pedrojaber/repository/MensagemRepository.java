package br.com.pedrojaber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pedrojaber.model.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

}
