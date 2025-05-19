package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Camera;
import br.com.fiap.Mottracker.enums.CameraStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

    Page<Camera> findByNomeCameraContainingIgnoreCase(String nomeCamera, Pageable pageable);

    Page<Camera> findByStatus(CameraStatus status, Pageable pageable);

}
