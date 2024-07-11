package com.jisus.forohub.models.datos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosModificarTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje)
{
}
