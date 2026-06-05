package br.com.terrasense.repository;

import br.com.terrasense.model.Propriedade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {

    Page<Propriedade> findByUsuarioIdUsuario(Long idUsuario, Pageable pageable);

    Page<Propriedade> findByTipoPropriedadeContainingIgnoreCase(
            String tipoPropriedade,
            Pageable pageable
    );
}