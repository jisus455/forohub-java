package com.jisus.forohub.models.model;

import com.jisus.forohub.models.datos.DatosAgregarTopico;
import com.jisus.forohub.models.datos.DatosModificarTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fecha_creacion;

    private Long id_usuario;

    private String nombre_curso;

    public Topico(DatosAgregarTopico datos) {
        this.id_usuario = datos.idUsuario();
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.nombre_curso = datos.nombreCurso();
        this.fecha_creacion = LocalDateTime.now();
    }

    public void modificarTopico(DatosModificarTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
    }


}
