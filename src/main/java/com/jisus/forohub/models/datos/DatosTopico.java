package com.jisus.forohub.models.datos;

import com.jisus.forohub.models.model.Topico;

import java.time.LocalDateTime;

public record DatosTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, Long idUsuario, String nombreCurso) {

    public DatosTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(), topico.getId_usuario(), topico.getNombre_curso());
    }
}
