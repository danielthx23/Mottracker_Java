package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record LayoutPatioRequestDto(

        String descricao,

        @NotNull
        BigDecimal largura,

        @NotNull
        BigDecimal comprimento,

        BigDecimal altura,

        @NotNull
        Long patioId,

        List<QrCodePontoRequestDto> qrCodesLayoutPatio

) {}
