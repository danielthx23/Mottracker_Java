package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.MotoRequestDto;
import br.com.fiap.Mottracker.enums.Estados;
import br.com.fiap.Mottracker.model.Contrato;
import br.com.fiap.Mottracker.model.Moto;
import br.com.fiap.Mottracker.model.Patio;
import br.com.fiap.Mottracker.repository.ContratoRepository;
import br.com.fiap.Mottracker.repository.MotoRepository;
import br.com.fiap.Mottracker.repository.PatioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final ContratoRepository contratoRepository;
    private final PatioRepository patioRepository;

    public Page<Moto> getAll(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    public Moto getById(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com ID: " + id));
    }

    public Moto getByPlaca(String placa) {
        return motoRepository.findByPlacaMoto(placa)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com placa: " + placa));
    }

    public Page<Moto> getByEstado(String estado, Pageable pageable) {
        Estados estadoEnum = Estados.valueOf(estado.toUpperCase());
        return motoRepository.findByEstadoMoto(estadoEnum, pageable);
    }

    public Page<Moto> getByContratoId(Long contratoId, Pageable pageable) {
        return motoRepository.findByContratoMoto_IdContrato(contratoId, pageable);
    }

    public java.util.List<Moto> getDisponiveis() {
        return motoRepository.findByEstadoMoto(Estados.NO_PATIO);
    }

    public Moto create(MotoRequestDto dto) {
        Moto moto = convertToEntity(dto);
        return motoRepository.save(moto);
    }

    public Moto update(Long id, MotoRequestDto dto) {
        if (!motoRepository.existsById(id)) {
            throw new EntityNotFoundException("Moto não encontrada com ID: " + id);
        }

        Moto moto = convertToEntity(dto);
        moto.setIdMoto(id);
        return motoRepository.save(moto);
    }

    public void delete(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new EntityNotFoundException("Moto não encontrada com ID: " + id);
        }
        motoRepository.deleteById(id);
    }

    private Moto convertToEntity(MotoRequestDto dto) {
        Moto moto = new Moto();
        moto.setPlacaMoto(dto.placaMoto());
        moto.setModeloMoto(dto.modeloMoto());
        moto.setAnoMoto(dto.anoMoto());
        moto.setIdentificadorMoto(dto.identificadorMoto());
        moto.setQuilometragemMoto(dto.quilometragemMoto());
        moto.setEstadoMoto(dto.estadoMoto());
        moto.setCondicoesMoto(dto.condicoesMoto());

        if (dto.contratoMotoId() != null) {
            Contrato contrato = contratoRepository.findById(dto.contratoMotoId())
                    .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado com ID: " + dto.contratoMotoId()));
            moto.setContratoMoto(contrato);
        }

        if (dto.motoPatioAtualId() != null) {
            Patio patioAtual = patioRepository.findById(dto.motoPatioAtualId())
                    .orElseThrow(() -> new EntityNotFoundException("Pátio atual não encontrado com ID: " + dto.motoPatioAtualId()));
            moto.setMotoPatioAtual(patioAtual);
        }

        if (dto.motoPatioOrigemId() != null) {
            Patio patioOrigem = patioRepository.findById(dto.motoPatioOrigemId())
                    .orElseThrow(() -> new EntityNotFoundException("Pátio de origem não encontrado com ID: " + dto.motoPatioOrigemId()));
            moto.setMotoPatioOrigemId(patioOrigem.getIdPatio());
        }

        return moto;
    }
}
