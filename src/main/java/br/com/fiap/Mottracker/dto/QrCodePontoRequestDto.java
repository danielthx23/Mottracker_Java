package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.NotNull;

public record QrCodePontoRequestDto(

        @NotNull
        String identificadorQrCode,

        float posX,

        float posY,

        @NotNull
        Long layoutPatioId

) {}
