package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.*;
import br.com.fiap.Mottracker.model.*;
import br.com.fiap.Mottracker.repository.PatioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatioService {

    private final PatioRepository patioRepository;

    public Page<Patio> getAll(Pageable pageable) {
        return patioRepository.findAll(pageable);
    }

    public Patio getById(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + id));
    }

    public Page<Patio> findByNomePatioContainingIgnoreCase(String nomePatio, Pageable pageable) {
        return patioRepository.findByNomePatioContainingIgnoreCase(nomePatio, pageable);
    }

    public Page<Patio> findByMotosDisponiveisPatioGreaterThan(int quantidade, Pageable pageable) {
        return patioRepository.findByMotosDisponiveisPatioGreaterThan(quantidade, pageable);
    }

    public Page<Patio> findByDataPatioAfter(LocalDateTime data, Pageable pageable) {
        return patioRepository.findByDataPatioAfter(data, pageable);
    }

    public Page<Patio> findByDataPatioBefore(LocalDateTime data, Pageable pageable) {
        return patioRepository.findByDataPatioBefore(data, pageable);
    }

    public Patio create(PatioRequestDto dto) {
        Patio patio = new Patio();
        patio.setNomePatio(dto.nomePatio());
        patio.setMotosTotaisPatio(dto.motosTotaisPatio());
        patio.setMotosDisponiveisPatio(dto.motosDisponiveisPatio());
        patio.setDataPatio(dto.dataPatio());

        patio.setEnderecoPatio(fromEnderecoDto(dto.enderecoPatio(), patio));
        patio.setLayoutPatio(fromLayoutDto(dto.layoutPatio(), patio));
        patio.setCamerasPatio(fromCameraDtoList(dto.camerasPatio(), patio));

        return patioRepository.save(patio);
    }

    public Patio update(Long id, PatioRequestDto dto) {
        Patio existente = patioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + id));

        existente.setNomePatio(dto.nomePatio());
        existente.setMotosTotaisPatio(dto.motosTotaisPatio());
        existente.setMotosDisponiveisPatio(dto.motosDisponiveisPatio());
        existente.setDataPatio(dto.dataPatio());

        existente.setEnderecoPatio(fromEnderecoDto(dto.enderecoPatio(), existente));
        existente.setLayoutPatio(fromLayoutDto(dto.layoutPatio(), existente));
        existente.setCamerasPatio(fromCameraDtoList(dto.camerasPatio(), existente));

        return patioRepository.save(existente);
    }

    public void delete(Long id) {
        if (!patioRepository.existsById(id)) {
            throw new EntityNotFoundException("Pátio não encontrado com ID: " + id);
        }
        patioRepository.deleteById(id);
    }

    private Endereco fromEnderecoDto(EnderecoRequestDto dto, Patio patio) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setReferencia(dto.referencia());
        endereco.setPatioEndereco(patio);
        return endereco;
    }

    private LayoutPatio fromLayoutDto(LayoutPatioRequestDto dto, Patio patio) {
        LayoutPatio layout = new LayoutPatio();
        layout.setDescricao(dto.descricao());
        layout.setComprimento(dto.comprimento());
        layout.setLargura(dto.largura());
        layout.setAltura(dto.altura());
        layout.setPatioLayoutPatio(patio);

        if (dto.qrCodesLayoutPatio() != null) {
            List<QrCodePonto> qrCodes = dto.qrCodesLayoutPatio().stream().map(qrDto -> {
                QrCodePonto qr = new QrCodePonto();
                qr.setIdentificadorQrCode(qrDto.identificadorQrCode());
                qr.setPosX(qrDto.posX());
                qr.setPosY(qrDto.posY());
                qr.setLayoutPatio(layout);
                return qr;
            }).collect(Collectors.toList());
            layout.setQrCodesLayoutPatio(qrCodes);
        } else {
            layout.setQrCodesLayoutPatio(List.of());
        }

        return layout;
    }

    private List<Camera> fromCameraDtoList(List<CameraRequestDto> dtos, Patio patio) {
        if (dtos == null) return List.of();
        return dtos.stream().map(dto -> {
            Camera camera = new Camera();
            camera.setIpCamera(dto.ipCamera());
            camera.setNomeCamera(dto.nomeCamera());
            camera.setStatus(dto.status());
            camera.setPosX(dto.posX());
            camera.setPosY(dto.posY());
            camera.setPatio(patio);
            return camera;
        }).collect(Collectors.toList());
    }
}
