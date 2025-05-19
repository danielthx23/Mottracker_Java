package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContratoRequestDto(

        String clausulasContrato,

        @NotNull
        LocalDateTime dataDeEntradaContrato,

        @NotNull
        LocalDateTime horarioDeDevolucaoContrato,

        @NotNull
        LocalDateTime dataDeExpiracaoContrato,

        boolean renovacaoAutomatica,

        BigDecimal valorToralContrato,

        @NotNull
        Integer quantidadeParcelas,

        @NotNull
        Long usuarioId,

        @NotNull
        Long motoId

) {}
