package br.com.terrasense.dto.plantacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PlantacaoRequestDTO(

        @NotBlank
        @Size(max = 150)
        String nomePlantacao,

        @NotBlank
        @Size(max = 80)
        String tipoPlantacao,

        @Positive
        double areaHectares,

        LocalDate dataPlantio,

        @NotBlank
        @Size(max = 20)
        String statusPlantacao,

        @NotNull
        Long idPropriedade

) {
}