package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TelefoneRequestDto(

        @NotBlank
        @Size(max = 20)
        String numero,

        @NotBlank
        @Size(max = 30)
        String tipo,

        @NotNull
        Long usuarioId

) {}
