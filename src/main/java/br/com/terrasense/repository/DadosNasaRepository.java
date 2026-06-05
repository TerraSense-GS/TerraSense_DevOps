package br.com.terrasense.repository;

import br.com.terrasense.model.DadosNasa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosNasaRepository extends JpaRepository<DadosNasa, Long> {

    Page<DadosNasa> findByPlantacaoIdPlantacao(Long idPlantacao, Pageable pageable);
}