package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.QrCodePonto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodePontoRepository extends JpaRepository<QrCodePonto, Long> {
    QrCodePonto findByIdentificadorQrCodeIgnoreCase(String identificadorQrCode);

    Page<QrCodePonto> findByLayoutPatio_IdLayoutPatio(Long layoutPatioId, Pageable pageable);

    Page<QrCodePonto> findByPosXBetween(float startPosX, float endPosX, Pageable pageable);

    Page<QrCodePonto> findByPosYBetween(float startPosY, float endPosY, Pageable pageable);
}

