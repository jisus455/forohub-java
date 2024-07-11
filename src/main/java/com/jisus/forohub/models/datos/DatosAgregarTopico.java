package com.jisus.forohub.models.datos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAgregarTopico(
        @NotNull Long idUsuario,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotBlank String nombreCurso)
{
}
