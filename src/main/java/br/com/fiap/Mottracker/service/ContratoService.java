package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.ContratoRequestDto;
import br.com.fiap.Mottracker.model.Contrato;
import br.com.fiap.Mottracker.model.Moto;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.repository.ContratoRepository;
import br.com.fiap.Mottracker.repository.MotoRepository;
import br.com.fiap.Mottracker.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MotoRepository motoRepository;

    public Page<Contrato> getAll(Pageable pageable) {
        return contratoRepository.findAll(pageable);
    }

    public Contrato getById(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado com ID: " + id));
    }

    public Page<Contrato> getAtivos(Pageable pageable) {
        return contratoRepository.findByAtivoContrato(1, pageable);
    }

    public Page<Contrato> getByUsuarioId(Long usuarioId, Pageable pageable) {
        return contratoRepository.findByUsuarioContrato_IdUsuario(usuarioId, pageable);
    }

    public Page<Contrato> getByUsuario(Long usuarioId, Pageable pageable) {
        return contratoRepository.findByUsuarioContrato_IdUsuario(usuarioId, pageable);
    }

    public Page<Contrato> getByMotoId(Long motoId, Pageable pageable) {
        return contratoRepository.findByMotoContrato_IdMoto(motoId, pageable);
    }

    public Page<Contrato> getRenovacaoAutomatica(Pageable pageable) {
        return contratoRepository.findByRenovacaoAutomaticaContrato(1, pageable);
    }

    public Page<Contrato> getValidos(Pageable pageable) {
        return contratoRepository.findByDataDeExpiracaoContratoAfter(LocalDateTime.now(), pageable);
    }

    public Page<Contrato> getByDataEntradaBetween(String inicio, String fim, Pageable pageable) {
        LocalDateTime start = LocalDateTime.parse(inicio);
        LocalDateTime end = LocalDateTime.parse(fim);
        return contratoRepository.findByDataDeEntradaContratoBetween(start, end, pageable);
    }

    public Contrato create(ContratoRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.usuarioId()));
        Moto moto = motoRepository.findById(dto.motoId())
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com ID: " + dto.motoId()));

        Contrato contrato = new Contrato();
        contrato.setClausulasContrato(dto.clausulasContrato());
        contrato.setDataDeEntradaContrato(dto.dataDeEntradaContrato());
        contrato.setHorarioDeDevolucaoContrato(dto.horarioDeDevolucaoContrato());
        contrato.setDataDeExpiracaoContrato(dto.dataDeExpiracaoContrato());
        contrato.setRenovacaoAutomatica(dto.renovacaoAutomatica());
        contrato.setAtivoContrato(1); // contrato é ativo ao ser criado
        contrato.setValorToralContrato(dto.valorToralContrato());
        contrato.setQuantidadeParcelas(dto.quantidadeParcelas());
        contrato.setUsuarioContrato(usuario);
        contrato.setMotoContrato(moto);

        return contratoRepository.save(contrato);
    }

    public Contrato update(Long id, ContratoRequestDto dto) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado com ID: " + id));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.usuarioId()));
        Moto moto = motoRepository.findById(dto.motoId())
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com ID: " + dto.motoId()));

        contrato.setClausulasContrato(dto.clausulasContrato());
        contrato.setDataDeEntradaContrato(dto.dataDeEntradaContrato());
        contrato.setHorarioDeDevolucaoContrato(dto.horarioDeDevolucaoContrato());
        contrato.setDataDeExpiracaoContrato(dto.dataDeExpiracaoContrato());
        contrato.setRenovacaoAutomatica(dto.renovacaoAutomatica());
        contrato.setValorToralContrato(dto.valorToralContrato());
        contrato.setQuantidadeParcelas(dto.quantidadeParcelas());
        contrato.setUsuarioContrato(usuario);
        contrato.setMotoContrato(moto);

        return contratoRepository.save(contrato);
    }

    public void delete(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrato não encontrado com ID: " + id);
        }
        contratoRepository.deleteById(id);
    }

    public void renovarContrato(Long id) {
        Contrato contrato = getById(id);
        contrato.setDataUltimaRenovacaoContrato(LocalDateTime.now());
        contrato.setNumeroRenovacoesContrato(contrato.getNumeroRenovacoesContrato() + 1);
        contrato.setDataDeExpiracaoContrato(contrato.getDataDeExpiracaoContrato().plusDays(30));
        contratoRepository.save(contrato);
    }

    public void cancelarContrato(Long id) {
        Contrato contrato = getById(id);
        contrato.setAtivoContrato(0); // 0 = false, 1 = true
        contratoRepository.save(contrato);
    }
}
