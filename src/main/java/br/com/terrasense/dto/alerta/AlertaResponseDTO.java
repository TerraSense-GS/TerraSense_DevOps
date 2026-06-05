package br.com.terrasense.dto.alerta;

import java.time.LocalDate;

public record AlertaResponseDTO(
        Long idAlerta,
        String tipoAlerta,
        String mensagem,
        String nivelAlerta,
        String statusAlerta,
        LocalDate dataAlerta,
        Long idPlantacao

) {
}