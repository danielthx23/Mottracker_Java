package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.CameraRequestDto;
import br.com.fiap.Mottracker.enums.CameraStatus;
import br.com.fiap.Mottracker.model.Camera;
import br.com.fiap.Mottracker.model.Patio;
import br.com.fiap.Mottracker.repository.CameraRepository;
import br.com.fiap.Mottracker.repository.PatioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;
    private final PatioRepository patioRepository;

    public Page<Camera> getAll(Pageable pageable) {
        return cameraRepository.findAll(pageable);
    }

    public Camera getById(Long id) {
        return cameraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Camera não encontrada com ID: " + id));
    }

    public Page<Camera> getByNome(String nome, Pageable pageable) {
        return cameraRepository.findByNomeCameraContainingIgnoreCase(nome, pageable);
    }

    public Page<Camera> getByStatus(CameraStatus status, Pageable pageable) {
        return cameraRepository.findByStatus(status, pageable);
    }

    public Camera create(CameraRequestDto dto) {
        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.patioId()));

        Camera camera = new Camera();
        camera.setNomeCamera(dto.nomeCamera());
        camera.setIpCamera(dto.ipCamera());
        camera.setStatus(dto.status());
        camera.setPosX(dto.posX());
        camera.setPosY(dto.posY());
        camera.setPatio(patio);

        return cameraRepository.save(camera);
    }

    public Camera update(Long id, CameraRequestDto dto) {
        Camera camera = cameraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Camera não encontrada com ID: " + id));

        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.patioId()));

        camera.setNomeCamera(dto.nomeCamera());
        camera.setIpCamera(dto.ipCamera());
        camera.setStatus(dto.status());
        camera.setPosX(dto.posX());
        camera.setPosY(dto.posY());
        camera.setPatio(patio);

        return cameraRepository.save(camera);
    }

    public void delete(Long id) {
        if (!cameraRepository.existsById(id)) {
            throw new EntityNotFoundException("Camera não encontrada com ID: " + id);
        }
        cameraRepository.deleteById(id);
    }
}