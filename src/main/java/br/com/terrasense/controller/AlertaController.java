package br.com.terrasense.controller;

import br.com.terrasense.dto.alerta.AlertaRequestDTO;
import br.com.terrasense.dto.alerta.AlertaResponseDTO;
import br.com.terrasense.service.AlertaService;
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
@RequestMapping("/alertas")
@Tag(name = "Alertas", description = "Endpoints para gerenciamento de alertas climáticos")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private PagedResourcesAssembler<AlertaResponseDTO> pagedAssembler;

    @PostMapping
    @Operation(summary = "Cadastrar alerta", description = "Cadastra um novo alerta climático")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Plantação não encontrada")
    })
    public ResponseEntity<EntityModel<AlertaResponseDTO>> cadastrar(
            @RequestBody @Valid AlertaRequestDTO dto
    ) {
        AlertaResponseDTO alerta = alertaService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adicionarLinks(alerta));
    }

    @GetMapping
    @Operation(summary = "Listar alertas", description = "Retorna uma lista paginada de alertas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<AlertaResponseDTO>>> listar(
            Pageable pageable
    ) {
        Page<AlertaResponseDTO> alertas = alertaService.listar(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(alertas, this::adicionarLinks)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID", description = "Retorna um alerta pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    public ResponseEntity<EntityModel<AlertaResponseDTO>> buscarPorId(
            @PathVariable Long id
    ) {
        AlertaResponseDTO alerta = alertaService.buscarPorId(id);

        return ResponseEntity.ok(adicionarLinks(alerta));
    }

    @GetMapping("/plantacao/{idPlantacao}")
    @Operation(summary = "Buscar alertas por plantação", description = "Retorna alertas vinculados a uma plantação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por plantação realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<AlertaResponseDTO>>> buscarPorPlantacao(
            @PathVariable Long idPlantacao,
            Pageable pageable
    ) {
        Page<AlertaResponseDTO> alertas =
                alertaService.buscarPorPlantacao(idPlantacao, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(alertas, this::adicionarLinks)
        );
    }

    @GetMapping("/status")
    @Operation(summary = "Buscar alertas por status", description = "Retorna alertas de acordo com o status informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por status realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<AlertaResponseDTO>>> buscarPorStatus(
            @RequestParam String statusAlerta,
            Pageable pageable
    ) {
        Page<AlertaResponseDTO> alertas =
                alertaService.buscarPorStatus(statusAlerta, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(alertas, this::adicionarLinks)
        );
    }

    @GetMapping("/nivel")
    @Operation(summary = "Buscar alertas por nível", description = "Retorna alertas de acordo com o nível informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por nível realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<AlertaResponseDTO>>> buscarPorNivel(
            @RequestParam String nivelAlerta,
            Pageable pageable
    ) {
        Page<AlertaResponseDTO> alertas =
                alertaService.buscarPorNivel(nivelAlerta, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(alertas, this::adicionarLinks)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alerta", description = "Atualiza os dados de um alerta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Alerta ou plantação não encontrado")
    })
    public ResponseEntity<EntityModel<AlertaResponseDTO>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AlertaRequestDTO dto
    ) {
        AlertaResponseDTO alerta = alertaService.atualizar(id, dto);

        return ResponseEntity.ok(adicionarLinks(alerta));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar alerta", description = "Remove um alerta pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<AlertaResponseDTO> adicionarLinks(
            AlertaResponseDTO alerta
    ) {
        return EntityModel.of(
                alerta,
                linkTo(methodOn(AlertaController.class)
                        .buscarPorId(alerta.idAlerta()))
                        .withSelfRel(),

                linkTo(methodOn(AlertaController.class)
                        .listar(Pageable.unpaged()))
                        .withRel("alertas"),

                linkTo(methodOn(PlantacaoController.class)
                        .buscarPorId(alerta.idPlantacao()))
                        .withRel("plantacao")
        );
    }
}