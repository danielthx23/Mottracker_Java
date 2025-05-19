package br.com.fiap.Mottracker.dto;

import br.com.fiap.Mottracker.enums.Estados;
import jakarta.validation.constraints.*;

public record MotoRequestDto(

        @NotNull
        String placaMoto,

        @NotNull
        String modeloMoto,

        @NotNull
        int anoMoto,

        String identificadorMoto,

        int quilometragemMoto,

        Estados estadoMoto,

        String condicoesMoto,

        Long contratoMotoId,

        Long motoPatioAtualId,

        Long motoPatioOrigemId

) {}
