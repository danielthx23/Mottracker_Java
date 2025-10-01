package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record PatioRequestDto(

        @NotBlank
        @Size(max = 100)
        String nomePatio,

        @Min(0)
        int motosTotaisPatio,

        @Min(0)
        int motosDisponiveisPatio,

        LocalDate dataPatio,

        LayoutPatioRequestDto layoutPatio,

        EnderecoRequestDto enderecoPatio,

        List<CameraRequestDto> camerasPatio,

        List<MotoRequestDto> motosPatioAtual

) {}
