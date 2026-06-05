package br.com.terrasense.controller;

import br.com.terrasense.dto.plantacao.PlantacaoRequestDTO;
import br.com.terrasense.dto.plantacao.PlantacaoResponseDTO;
import br.com.terrasense.service.PlantacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/plantacoes")
@Tag(name = "Plantações", description = "Endpoints para gerenciamento de plantações")
public class PlantacaoController {

    @Autowired
    private PlantacaoService plantacaoService;

    @Autowired
    private PagedResourcesAssembler<PlantacaoResponseDTO> pagedAssembler;

    @PostMapping
    @Operation(summary = "Cadastrar plantação", description = "Cadastra uma nova plantação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plantação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    public ResponseEntity<EntityModel<PlantacaoResponseDTO>> cadastrar(
            @RequestBody @Valid PlantacaoRequestDTO dto
    ) {
        PlantacaoResponseDTO plantacao = plantacaoService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adicionarLinks(plantacao));
    }

    @GetMapping
    @Operation(summary = "Listar plantações", description = "Retorna uma lista paginada de plantações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de plantações retornada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PlantacaoResponseDTO>>> listar(
            Pageable pageable
    ) {
        Page<PlantacaoResponseDTO> plantacoes = plantacaoService.listar(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(plantacoes, this::adicionarLinks)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plantação por ID", description = "Retorna uma plantação pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plantação encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plantação não encontrada")
    })
    public ResponseEntity<EntityModel<PlantacaoResponseDTO>> buscarPorId(
            @PathVariable Long id
    ) {
        PlantacaoResponseDTO plantacao = plantacaoService.buscarPorId(id);

        return ResponseEntity.ok(adicionarLinks(plantacao));
    }

    @GetMapping("/propriedade/{idPropriedade}")
    @Operation(summary = "Buscar plantações por propriedade", description = "Retorna plantações vinculadas a uma propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por propriedade realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PlantacaoResponseDTO>>> buscarPorPropriedade(
            @PathVariable Long idPropriedade,
            Pageable pageable
    ) {
        Page<PlantacaoResponseDTO> plantacoes =
                plantacaoService.buscarPorPropriedade(idPropriedade, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(plantacoes, this::adicionarLinks)
        );
    }

    @GetMapping("/status")
    @Operation(summary = "Buscar plantações por status", description = "Retorna plantações de acordo com o status informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por status realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PlantacaoResponseDTO>>> buscarPorStatus(
            @RequestParam String statusPlantacao,
            Pageable pageable
    ) {
        Page<PlantacaoResponseDTO> plantacoes =
                plantacaoService.buscarPorStatus(statusPlantacao, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(plantacoes, this::adicionarLinks)
        );
    }

    @GetMapping("/tipo")
    @Operation(summary = "Buscar plantações por tipo", description = "Retorna plantações de acordo com o tipo informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por tipo realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PlantacaoResponseDTO>>> buscarPorTipo(
            @RequestParam String tipoPlantacao,
            Pageable pageable
    ) {
        Page<PlantacaoResponseDTO> plantacoes =
                plantacaoService.buscarPorTipo(tipoPlantacao, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(plantacoes, this::adicionarLinks)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plantação", description = "Atualiza os dados de uma plantação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plantação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Plantação ou propriedade não encontrada")
    })
    public ResponseEntity<EntityModel<PlantacaoResponseDTO>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PlantacaoRequestDTO dto
    ) {
        PlantacaoResponseDTO plantacao = plantacaoService.atualizar(id, dto);

        return ResponseEntity.ok(adicionarLinks(plantacao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar plantação", description = "Remove uma plantação pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plantação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plantação não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        plantacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<PlantacaoResponseDTO> adicionarLinks(
            PlantacaoResponseDTO plantacao
    ) {
        return EntityModel.of(
                plantacao,
                linkTo(methodOn(PlantacaoController.class)
                        .buscarPorId(plantacao.idPlantacao()))
                        .withSelfRel(),

                linkTo(methodOn(PlantacaoController.class)
                        .listar(Pageable.unpaged()))
                        .withRel("plantacoes"),

                linkTo(methodOn(PropriedadeController.class)
                        .buscarPorId(plantacao.idPropriedade()))
                        .withRel("propriedade")
        );
    }
}