package com.jisus.forohub.models.datos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAutenticacionUsuario(
        @NotBlank @Email String email,
        @NotBlank String clave)
{
}
