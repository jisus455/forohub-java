package com.jisus.forohub.repository;

import com.jisus.forohub.models.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findAllById(Long id);

    Optional<Topico> findByTituloOrMensaje(String titulo, String mensaje);
}
