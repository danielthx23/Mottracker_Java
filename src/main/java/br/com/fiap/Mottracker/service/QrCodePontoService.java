package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.QrCodePontoRequestDto;
import br.com.fiap.Mottracker.model.LayoutPatio;
import br.com.fiap.Mottracker.model.QrCodePonto;
import br.com.fiap.Mottracker.repository.LayoutPatioRepository;
import br.com.fiap.Mottracker.repository.QrCodePontoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QrCodePontoService {

    private final QrCodePontoRepository qrCodePontoRepository;
    private final LayoutPatioRepository layoutPatioRepository;

    public Page<QrCodePonto> getAll(Pageable pageable) {
        return qrCodePontoRepository.findAll(pageable);
    }

    public QrCodePonto getById(Long id) {
        return qrCodePontoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QR Code não encontrado com ID: " + id));
    }

    public QrCodePonto getByIdentificador(String identificador) {
        QrCodePonto qr = qrCodePontoRepository.findByIdentificadorQrCodeIgnoreCase(identificador);
        if (qr == null) {
            throw new EntityNotFoundException("QR Code com identificador '" + identificador + "' não encontrado");
        }
        return qr;
    }

    public Page<QrCodePonto> findByLayoutPatioId(Long layoutPatioId, Pageable pageable) {
        return qrCodePontoRepository.findByLayoutPatio_IdLayoutPatio(layoutPatioId, pageable);
    }

    public Page<QrCodePonto> findByPosXBetween(float start, float end, Pageable pageable) {
        return qrCodePontoRepository.findByPosXBetween(start, end, pageable);
    }

    public Page<QrCodePonto> findByPosYBetween(float start, float end, Pageable pageable) {
        return qrCodePontoRepository.findByPosYBetween(start, end, pageable);
    }

    public QrCodePonto create(QrCodePontoRequestDto dto) {
        if (qrCodePontoRepository.findByIdentificadorQrCodeIgnoreCase(dto.identificadorQrCode()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "QR Code com identificador já existe");
        }

        QrCodePonto qrCodePonto = fromDto(dto);
        return qrCodePontoRepository.save(qrCodePonto);
    }

    public QrCodePonto update(Long id, QrCodePontoRequestDto dto) {
        if (!qrCodePontoRepository.existsById(id)) {
            throw new EntityNotFoundException("QR Code não encontrado com ID: " + id);
        }
        QrCodePonto qrCodePonto = fromDto(dto);
        qrCodePonto.setIdQrCodePonto(id);
        return qrCodePontoRepository.save(qrCodePonto);
    }

    public void delete(Long id) {
        if (!qrCodePontoRepository.existsById(id)) {
            throw new EntityNotFoundException("QR Code não encontrado com ID: " + id);
        }
        qrCodePontoRepository.deleteById(id);
    }

    private QrCodePonto fromDto(QrCodePontoRequestDto dto) {
        LayoutPatio layoutPatio = layoutPatioRepository.findById(dto.layoutPatioId())
                .orElseThrow(() -> new EntityNotFoundException("Layout do Pátio não encontrado com ID: " + dto.layoutPatioId()));

        QrCodePonto ponto = new QrCodePonto();
        ponto.setIdentificadorQrCode(dto.identificadorQrCode());
        ponto.setPosX(dto.posX());
        ponto.setPosY(dto.posY());
        ponto.setLayoutPatio(layoutPatio);
        return ponto;
    }
}