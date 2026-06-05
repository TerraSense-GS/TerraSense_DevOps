package br.com.terrasense.dto.plantacao;

import java.time.LocalDate;

public record PlantacaoResponseDTO(
        Long idPlantacao,
        String nomePlantacao,
        String tipoPlantacao,
        double areaHectares,
        LocalDate dataPlantio,
        String statusPlantacao,
        Long idPropriedade
) {
}