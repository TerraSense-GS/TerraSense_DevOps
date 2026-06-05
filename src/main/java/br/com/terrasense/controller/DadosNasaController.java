package br.com.terrasense.controller;

import br.com.terrasense.dto.dadosnasa.DadosNasaRequestDTO;
import br.com.terrasense.dto.dadosnasa.DadosNasaResponseDTO;
import br.com.terrasense.service.DadosNasaService;
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
@RequestMapping("/dados-nasa")
@Tag(name = "Dados NASA", description = "Endpoints para gerenciamento de dados climáticos obtidos por satélite")
public class DadosNasaController {

    @Autowired
    private DadosNasaService dadosNasaService;

    @Autowired
    private PagedResourcesAssembler<DadosNasaResponseDTO> pagedAssembler;

    @PostMapping
    @Operation(summary = "Cadastrar dados NASA", description = "Cadastra um novo registro de dados NASA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados NASA cadastrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Plantação não encontrada")
    })
    public ResponseEntity<EntityModel<DadosNasaResponseDTO>> cadastrar(
            @RequestBody @Valid DadosNasaRequestDTO dto
    ) {
        DadosNasaResponseDTO dados = dadosNasaService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adicionarLinks(dados));
    }

    @GetMapping
    @Operation(summary = "Listar dados NASA", description = "Retorna uma lista paginada de dados NASA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dados NASA retornada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<DadosNasaResponseDTO>>> listar(
            Pageable pageable
    ) {
        Page<DadosNasaResponseDTO> dados = dadosNasaService.listar(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(dados, this::adicionarLinks)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dados NASA por ID", description = "Retorna um registro de dados NASA pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados NASA encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados NASA não encontrados")
    })
    public ResponseEntity<EntityModel<DadosNasaResponseDTO>> buscarPorId(
            @PathVariable Long id
    ) {
        DadosNasaResponseDTO dados = dadosNasaService.buscarPorId(id);

        return ResponseEntity.ok(adicionarLinks(dados));
    }

    @GetMapping("/plantacao/{idPlantacao}")
    @Operation(summary = "Buscar dados NASA por plantação", description = "Retorna dados NASA vinculados a uma plantação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por plantação realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<DadosNasaResponseDTO>>> buscarPorPlantacao(
            @PathVariable Long idPlantacao,
            Pageable pageable
    ) {
        Page<DadosNasaResponseDTO> dados =
                dadosNasaService.buscarPorPlantacao(idPlantacao, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(dados, this::adicionarLinks)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados NASA", description = "Atualiza os dados de um registro NASA existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados NASA atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Dados NASA ou plantação não encontrados")
    })
    public ResponseEntity<EntityModel<DadosNasaResponseDTO>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosNasaRequestDTO dto
    ) {
        DadosNasaResponseDTO dados = dadosNasaService.atualizar(id, dto);

        return ResponseEntity.ok(adicionarLinks(dados));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar dados NASA", description = "Remove um registro NASA pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados NASA deletados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados NASA não encontrados")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dadosNasaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<DadosNasaResponseDTO> adicionarLinks(
            DadosNasaResponseDTO dados
    ) {
        return EntityModel.of(
                dados,
                linkTo(methodOn(DadosNasaController.class)
                        .buscarPorId(dados.idDadoNasa()))
                        .withSelfRel(),

                linkTo(methodOn(DadosNasaController.class)
                        .listar(Pageable.unpaged()))
                        .withRel("dados-nasa"),

                linkTo(methodOn(PlantacaoController.class)
                        .buscarPorId(dados.idPlantacao()))
                        .withRel("plantacao")
        );
    }
}