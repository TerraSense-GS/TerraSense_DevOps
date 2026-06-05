package br.com.terrasense.service;

import br.com.terrasense.dto.plantacao.PlantacaoRequestDTO;
import br.com.terrasense.dto.plantacao.PlantacaoResponseDTO;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.Plantacao;
import br.com.terrasense.model.Propriedade;
import br.com.terrasense.repository.PlantacaoRepository;
import br.com.terrasense.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantacaoService {

    private final PlantacaoRepository plantacaoRepository;
    private final PropriedadeRepository propriedadeRepository;

    @Transactional(readOnly = true)
    public Page<PlantacaoResponseDTO> listar(Pageable pageable) {
        return plantacaoRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public PlantacaoResponseDTO buscarPorId(Long id) {
        Plantacao plantacao = plantacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plantação não encontrada com ID: " + id)
                );

        return toResponseDTO(plantacao);
    }

    @Transactional(readOnly = true)
    public Page<PlantacaoResponseDTO> buscarPorPropriedade(
            Long idPropriedade,
            Pageable pageable
    ) {
        return plantacaoRepository
                .findByPropriedadeIdPropriedade(idPropriedade, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<PlantacaoResponseDTO> buscarPorStatus(
            String statusPlantacao,
            Pageable pageable
    ) {
        return plantacaoRepository
                .findByStatusPlantacaoIgnoreCase(statusPlantacao, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<PlantacaoResponseDTO> buscarPorTipo(
            String tipoPlantacao,
            Pageable pageable
    ) {
        return plantacaoRepository
                .findByTipoPlantacaoContainingIgnoreCase(tipoPlantacao, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional
    public PlantacaoResponseDTO cadastrar(PlantacaoRequestDTO dto) {
        Propriedade propriedade = propriedadeRepository
                .findById(dto.idPropriedade())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com ID: " + dto.idPropriedade())
                );

        Plantacao plantacao = new Plantacao();

        plantacao.setNomePlantacao(dto.nomePlantacao());
        plantacao.setTipoPlantacao(dto.tipoPlantacao());
        plantacao.setAreaHectares(dto.areaHectares());
        plantacao.setDataPlantio(dto.dataPlantio());
        plantacao.setStatusPlantacao(dto.statusPlantacao());
        plantacao.setPropriedade(propriedade);

        Plantacao plantacaoSalva = plantacaoRepository.save(plantacao);

        return toResponseDTO(plantacaoSalva);
    }

    @Transactional
    public PlantacaoResponseDTO atualizar(
            Long id,
            PlantacaoRequestDTO dto
    ) {
        Plantacao plantacao = plantacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plantação não encontrada com ID: " + id)
                );

        Propriedade propriedade = propriedadeRepository
                .findById(dto.idPropriedade())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com ID: " + dto.idPropriedade())
                );

        plantacao.setNomePlantacao(dto.nomePlantacao());
        plantacao.setTipoPlantacao(dto.tipoPlantacao());
        plantacao.setAreaHectares(dto.areaHectares());
        plantacao.setDataPlantio(dto.dataPlantio());
        plantacao.setStatusPlantacao(dto.statusPlantacao());
        plantacao.setPropriedade(propriedade);

        Plantacao plantacaoAtualizada = plantacaoRepository.save(plantacao);

        return toResponseDTO(plantacaoAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        Plantacao plantacao = plantacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plantação não encontrada com ID: " + id)
                );

        plantacaoRepository.delete(plantacao);
    }

    private PlantacaoResponseDTO toResponseDTO(Plantacao plantacao) {
        return new PlantacaoResponseDTO(
                plantacao.getIdPlantacao(),
                plantacao.getNomePlantacao(),
                plantacao.getTipoPlantacao(),
                plantacao.getAreaHectares(),
                plantacao.getDataPlantio(),
                plantacao.getStatusPlantacao(),
                plantacao.getPropriedade().getIdPropriedade()
        );
    }
}