package br.com.fiap.Mottracker.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

public record PatioRequestDto(

        @NotNull
        String nomePatio,

        int motosTotaisPatio,

        int motosDisponiveisPatio,

        LocalDateTime dataPatio,

        LayoutPatioRequestDto layoutPatio,

        EnderecoRequestDto enderecoPatio,

        List<CameraRequestDto> camerasPatio,

        List<MotoRequestDto> motosPatioAtual

) {}
