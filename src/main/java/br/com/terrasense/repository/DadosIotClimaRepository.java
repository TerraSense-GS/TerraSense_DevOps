package br.com.terrasense.repository;

import br.com.terrasense.model.DadosIotClima;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosIotClimaRepository extends JpaRepository<DadosIotClima, Long> {

    Page<DadosIotClima> findByPlantacaoIdPlantacao(Long idPlantacao, Pageable pageable);
}