package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.NotNull;

public record UsuarioPermissaoRequestDto(

        @NotNull
        Long usuarioId,

        @NotNull
        Long permissaoId,

        String papel

) {}
