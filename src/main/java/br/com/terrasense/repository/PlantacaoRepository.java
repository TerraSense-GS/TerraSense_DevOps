package br.com.terrasense.repository;

import br.com.terrasense.model.Plantacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantacaoRepository extends JpaRepository<Plantacao, Long> {

    Page<Plantacao> findByPropriedadeIdPropriedade(Long idPropriedade, Pageable pageable);

    Page<Plantacao> findByStatusPlantacaoIgnoreCase(String statusPlantacao, Pageable pageable);

    Page<Plantacao> findByTipoPlantacaoContainingIgnoreCase(String tipoPlantacao, Pageable pageable);
}