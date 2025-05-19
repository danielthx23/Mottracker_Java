package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PermissaoRequestDto(

        @NotNull
        @Size(max = 100)
        String nomePermissao,

        String descricao

) {}
