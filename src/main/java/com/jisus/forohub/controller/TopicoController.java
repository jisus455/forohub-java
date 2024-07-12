package com.jisus.forohub.controller;

import com.jisus.forohub.models.datos.DatosAgregarTopico;
import com.jisus.forohub.models.datos.DatosTopico;
import com.jisus.forohub.models.datos.DatosModificarTopico;
import com.jisus.forohub.models.model.Topico;
import com.jisus.forohub.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Operation(summary = "obtener todos los topicos registrados")
    @GetMapping
    public ResponseEntity<Page<DatosTopico>> listadoTopicos(Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatosTopico :: new));
    }

    @Operation(summary = "obtener un topico especifico por id")
    @GetMapping("/{id}")
    public ResponseEntity<DatosTopico> obtenerTopico(@PathVariable Long id) {
        Optional<Topico> topico = repository.findAllById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new DatosTopico(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "agregar un nuevo topico")
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

    @Operation(summary = "modificar un topico existente por id")
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

    @Operation(summary = "borrar un topico existente por id")
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
