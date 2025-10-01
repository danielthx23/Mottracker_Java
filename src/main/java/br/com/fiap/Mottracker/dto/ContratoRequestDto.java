package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ContratoRequestDto(

        String clausulasContrato,

        @NotNull
        LocalDate dataDeEntradaContrato,

        LocalTime horarioDeDevolucaoContrato,

        @NotNull
        LocalDate dataDeExpiracaoContrato,

        Boolean renovacaoAutomatica,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal valorToralContrato,

        @NotNull
        @Min(1)
        Integer quantidadeParcelas,

        @NotNull
        Long usuarioId,

        @NotNull
        Long motoId

) {}
