package br.com.terrasense.controller;

import br.com.terrasense.dto.dadosiotclima.DadosIotClimaRequestDTO;
import br.com.terrasense.dto.dadosiotclima.DadosIotClimaResponseDTO;
import br.com.terrasense.service.DadosIotClimaService;
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
@RequestMapping("/dados-iot-clima")
@Tag(name = "Dados IoT Clima", description = "Endpoints para gerenciamento de dados climáticos coletados por sensores IoT")
public class DadosIotClimaController {

    @Autowired
    private DadosIotClimaService dadosIotClimaService;

    @Autowired
    private PagedResourcesAssembler<DadosIotClimaResponseDTO> pagedAssembler;

    @PostMapping
    @Operation(summary = "Cadastrar dados IoT", description = "Cadastra um novo registro climático de sensores IoT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados IoT cadastrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Plantação não encontrada")
    })
    public ResponseEntity<EntityModel<DadosIotClimaResponseDTO>> cadastrar(
            @RequestBody @Valid DadosIotClimaRequestDTO dto
    ) {
        DadosIotClimaResponseDTO dados = dadosIotClimaService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adicionarLinks(dados));
    }

    @GetMapping
    @Operation(summary = "Listar dados IoT", description = "Retorna uma lista paginada de dados climáticos IoT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dados IoT retornada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<DadosIotClimaResponseDTO>>> listar(
            Pageable pageable
    ) {
        Page<DadosIotClimaResponseDTO> dados = dadosIotClimaService.listar(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(dados, this::adicionarLinks)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dados IoT por ID", description = "Retorna um registro IoT pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados IoT encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados IoT não encontrados")
    })
    public ResponseEntity<EntityModel<DadosIotClimaResponseDTO>> buscarPorId(
            @PathVariable Long id
    ) {
        DadosIotClimaResponseDTO dados = dadosIotClimaService.buscarPorId(id);

        return ResponseEntity.ok(adicionarLinks(dados));
    }

    @GetMapping("/plantacao/{idPlantacao}")
    @Operation(summary = "Buscar dados IoT por plantação", description = "Retorna dados IoT vinculados a uma plantação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por plantação realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<DadosIotClimaResponseDTO>>> buscarPorPlantacao(
            @PathVariable Long idPlantacao,
            Pageable pageable
    ) {
        Page<DadosIotClimaResponseDTO> dados =
                dadosIotClimaService.buscarPorPlantacao(idPlantacao, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(dados, this::adicionarLinks)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados IoT", description = "Atualiza os dados de um registro IoT existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados IoT atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Dados IoT ou plantação não encontrados")
    })
    public ResponseEntity<EntityModel<DadosIotClimaResponseDTO>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosIotClimaRequestDTO dto
    ) {
        DadosIotClimaResponseDTO dados = dadosIotClimaService.atualizar(id, dto);

        return ResponseEntity.ok(adicionarLinks(dados));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar dados IoT", description = "Remove um registro IoT pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados IoT deletados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados IoT não encontrados")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dadosIotClimaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<DadosIotClimaResponseDTO> adicionarLinks(
            DadosIotClimaResponseDTO dados
    ) {
        return EntityModel.of(
                dados,
                linkTo(methodOn(DadosIotClimaController.class)
                        .buscarPorId(dados.idDadoIot()))
                        .withSelfRel(),

                linkTo(methodOn(DadosIotClimaController.class)
                        .listar(Pageable.unpaged()))
                        .withRel("dados-iot-clima"),

                linkTo(methodOn(PlantacaoController.class)
                        .buscarPorId(dados.idPlantacao()))
                        .withRel("plantacao")
        );
    }
}