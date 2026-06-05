package br.com.terrasense.service;

import br.com.terrasense.dto.dadosiotclima.DadosIotClimaRequestDTO;
import br.com.terrasense.dto.dadosiotclima.DadosIotClimaResponseDTO;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.DadosIotClima;
import br.com.terrasense.model.Plantacao;
import br.com.terrasense.repository.DadosIotClimaRepository;
import br.com.terrasense.repository.PlantacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DadosIotClimaService {

    private final DadosIotClimaRepository dadosIotClimaRepository;
    private final PlantacaoRepository plantacaoRepository;

    @Transactional(readOnly = true)
    public Page<DadosIotClimaResponseDTO> listar(Pageable pageable) {
        return dadosIotClimaRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public DadosIotClimaResponseDTO buscarPorId(Long id) {
        DadosIotClima dados = dadosIotClimaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dados IoT não encontrados com ID: " + id)
                );

        return toResponseDTO(dados);
    }

    @Transactional(readOnly = true)
    public Page<DadosIotClimaResponseDTO> buscarPorPlantacao(
            Long idPlantacao,
            Pageable pageable
    ) {
        return dadosIotClimaRepository
                .findByPlantacaoIdPlantacao(idPlantacao, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional
    public DadosIotClimaResponseDTO cadastrar(DadosIotClimaRequestDTO dto) {
        Plantacao plantacao = plantacaoRepository
                .findById(dto.idPlantacao())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plantação não encontrada com ID: " + dto.idPlantacao())
                );

        DadosIotClima dados = new DadosIotClima();

        dados.setTemperatura(dto.temperatura());
        dados.setUmidade(dto.umidade());
        dados.setChuva(dto.chuva());
        dados.setVentoKmh(dto.ventoKmh());
        dados.setClima(dto.clima());
        dados.setDataLeitura(dto.dataLeitura());
        dados.setPlantacao(plantacao);

        DadosIotClima dadosSalvo = dadosIotClimaRepository.save(dados);

        return toResponseDTO(dadosSalvo);
    }

    @Transactional
    public DadosIotClimaResponseDTO atualizar(
            Long id,
            DadosIotClimaRequestDTO dto
    ) {
        DadosIotClima dados = dadosIotClimaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dados IoT não encontrados com ID: " + id)
                );

        Plantacao plantacao = plantacaoRepository
                .findById(dto.idPlantacao())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plantação não encontrada com ID: " + dto.idPlantacao()
                        )
                );

        dados.setTemperatura(dto.temperatura());
        dados.setUmidade(dto.umidade());
        dados.setChuva(dto.chuva());
        dados.setVentoKmh(dto.ventoKmh());
        dados.setClima(dto.clima());
        dados.setDataLeitura(dto.dataLeitura());
        dados.setPlantacao(plantacao);

        DadosIotClima dadosAtualizado = dadosIotClimaRepository.save(dados);

        return toResponseDTO(dadosAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        DadosIotClima dados = dadosIotClimaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dados IoT não encontrados com ID: " + id)
                );

        dadosIotClimaRepository.delete(dados);
    }

    private DadosIotClimaResponseDTO toResponseDTO(DadosIotClima dados) {
        return new DadosIotClimaResponseDTO(
                dados.getIdDadoIot(),
                dados.getTemperatura(),
                dados.getUmidade(),
                dados.getChuva(),
                dados.getVentoKmh(),
                dados.getClima(),
                dados.getDataLeitura(),
                dados.getPlantacao().getIdPlantacao()
        );
    }
}