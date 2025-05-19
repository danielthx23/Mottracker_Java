package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDto(

        @NotBlank
        @Email
        String emailUsuario,

        @NotBlank
        String senhaUsuario

) {}
