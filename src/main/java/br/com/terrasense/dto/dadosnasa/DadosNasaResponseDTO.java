package br.com.terrasense.dto.dadosnasa;

import java.time.LocalDate;

public record DadosNasaResponseDTO(
        Long idDadoNasa,
        LocalDate dataReferencia,
        double radiacaoSolar,
        double ndvi,
        double temperaturaSat,
        double umidadeSat,
        double precipSat,
        LocalDate dataColeta,
        Long idPlantacao

) {
}