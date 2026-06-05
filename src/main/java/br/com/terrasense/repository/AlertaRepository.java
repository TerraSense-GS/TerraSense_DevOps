package br.com.terrasense.repository;

import br.com.terrasense.model.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Page<Alerta> findByPlantacaoIdPlantacao(Long idPlantacao, Pageable pageable);

    Page<Alerta> findByStatusAlertaIgnoreCase(String statusAlerta, Pageable pageable);

    Page<Alerta> findByNivelAlertaIgnoreCase(String nivelAlerta, Pageable pageable);
}