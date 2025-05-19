package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioRequestDto(

        @NotBlank
        String nomeUsuario,

        @NotBlank
        String cpfUsuario,

        @NotBlank
        String senhaUsuario,

        @NotBlank
        String cnhUsuario,

        @NotNull
        @Email
        String emailUsuario,

        LocalDateTime dataNascimentoUsuario,

        List<TelefoneRequestDto> telefones

) {}
