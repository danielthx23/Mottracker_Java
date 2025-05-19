package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.*;

public record EnderecoRequestDto(

        @NotBlank
        @Size(max = 150)
        String logradouro,

        @NotBlank
        @Size(max = 20)
        String numero,

        @Size(max = 100)
        String complemento,

        @NotBlank
        @Size(max = 100)
        String bairro,

        @NotBlank
        @Size(max = 100)
        String cidade,

        @NotBlank
        @Size(max = 2)
        String estado,

        @NotBlank
        @Size(max = 10)
        String cep,

        @Size(max = 100)
        String referencia,

        @NotNull
        Long patioId

) {}
