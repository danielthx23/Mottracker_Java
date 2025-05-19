package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.LayoutPatioRequestDto;
import br.com.fiap.Mottracker.dto.QrCodePontoRequestDto;
import br.com.fiap.Mottracker.model.LayoutPatio;
import br.com.fiap.Mottracker.model.Patio;
import br.com.fiap.Mottracker.model.QrCodePonto;
import br.com.fiap.Mottracker.repository.LayoutPatioRepository;
import br.com.fiap.Mottracker.repository.PatioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LayoutPatioService {

    private final LayoutPatioRepository layoutPatioRepository;
    private final PatioRepository patioRepository;

    public Page<LayoutPatio> getAll(Pageable pageable) {
        return layoutPatioRepository.findAll(pageable);
    }

    public LayoutPatio getById(Long id) {
        return layoutPatioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Layout não encontrado com ID: " + id));
    }

    public Page<LayoutPatio> findByPatioId(Long patioId, Pageable pageable) {
        return layoutPatioRepository.findByPatioLayoutPatio_IdPatio(patioId, pageable);
    }

    public Page<LayoutPatio> findByDataCriacaoBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return layoutPatioRepository.findByDataCriacaoBetween(start, end, pageable);
    }

    public LayoutPatio create(LayoutPatioRequestDto dto) {
        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.patioId()));

        LayoutPatio layout = new LayoutPatio();
        layout.setDescricao(dto.descricao());
        layout.setComprimento(dto.comprimento());
        layout.setLargura(dto.largura());
        layout.setAltura(dto.altura());
        layout.setPatioLayoutPatio(patio);

        LayoutPatio salvo = layoutPatioRepository.save(layout);
        salvarQrCodes(dto.qrCodesLayoutPatio(), salvo);

        return layoutPatioRepository.save(salvo);
    }

    public LayoutPatio update(Long id, LayoutPatioRequestDto dto) {
        LayoutPatio layout = layoutPatioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Layout não encontrado com ID: " + id));

        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.patioId()));

        layout.setDescricao(dto.descricao());
        layout.setComprimento(dto.comprimento());
        layout.setLargura(dto.largura());
        layout.setAltura(dto.altura());
        layout.setPatioLayoutPatio(patio);

        layout.getQrCodesLayoutPatio().clear();
        salvarQrCodes(dto.qrCodesLayoutPatio(), layout);

        return layoutPatioRepository.save(layout);
    }

    private void salvarQrCodes(List<QrCodePontoRequestDto> dtos, LayoutPatio layout) {
        if (dtos == null) return;

        List<QrCodePonto> qrCodes = dtos.stream().map(dto -> {
            QrCodePonto ponto = new QrCodePonto();
            ponto.setPosX(dto.posX());
            ponto.setPosY(dto.posY());
            ponto.setIdentificadorQrCode(dto.identificadorQrCode());
            ponto.setLayoutPatio(layout);
            return ponto;
        }).collect(Collectors.toList());

        layout.setQrCodesLayoutPatio(qrCodes);
    }

    public void delete(Long id) {
        if (!layoutPatioRepository.existsById(id)) {
            throw new EntityNotFoundException("Layout Patio não encontrado com ID: " + id);
        }
        layoutPatioRepository.deleteById(id);
    }
}
