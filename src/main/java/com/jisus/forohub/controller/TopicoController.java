package com.jisus.forohub.controller;

import com.jisus.forohub.models.datos.DatosAgregarTopico;
import com.jisus.forohub.models.datos.DatosTopico;
import com.jisus.forohub.models.datos.DatosModificarTopico;
import com.jisus.forohub.models.model.Topico;
import com.jisus.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @GetMapping
    public ResponseEntity<Page<DatosTopico>> listadoTopicos(Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatosTopico :: new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosTopico> obtenerTopico(@PathVariable Long id) {
        Optional<Topico> topico = repository.findAllById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new DatosTopico(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosTopico> agregarTopico(@RequestBody @Valid DatosAgregarTopico datos, UriComponentsBuilder uriBuilder) {
        Optional<Topico> topico = repository.findByTituloOrMensaje(datos.titulo(), datos.mensaje());
        if(topico.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            Topico topicoNuevo = new Topico(datos);
            repository.save(topicoNuevo);

            var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoNuevo.getId()).toUri();
            return ResponseEntity.created(uri).body(new DatosTopico(topicoNuevo));
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosTopico> modificarTopico(@PathVariable Long id, @RequestBody @Valid DatosModificarTopico datos) {
        Optional<Topico> topico = repository.findAllById(id);
        if(topico.isPresent()) {
            Optional<Topico> topicoGuardado = repository.findByTituloOrMensaje(datos.titulo(), datos.mensaje());
            if(!topicoGuardado.isPresent()) {
                topico.get().modificarTopico(datos);
                return ResponseEntity.ok(new DatosTopico(topico.get()));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarTopico(@PathVariable Long id) {
        Optional<Topico> topico = repository.findAllById(id);
        if(topico.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
