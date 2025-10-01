package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UsuarioEditDto(

        @NotBlank
        String nomeUsuario,

        @NotBlank
        String cpfUsuario,

        // Senha é opcional na edição - se vazia, mantém a atual
        String senhaUsuario,

        @NotBlank
        String cnhUsuario,

        @NotNull
        @Email
        String emailUsuario,

        LocalDate dataNascimentoUsuario,

        List<TelefoneRequestDto> telefones

) {}
