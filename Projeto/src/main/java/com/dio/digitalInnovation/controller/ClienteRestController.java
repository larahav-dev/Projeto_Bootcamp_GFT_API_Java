package com.dio.digitalInnovation.controller;

import com.dio.digitalInnovation.model.Cliente;
import com.dio.digitalInnovation.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    private final ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Listar todos os clientes")
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(
            summary = "Listar clientes com paginação",
            description = "Retorna clientes paginados e ordenados por um campo válido",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-index)", example = "0"),
                    @Parameter(name = "size", description = "Quantidade de registros por página", example = "10"),
                    @Parameter(name = "sort", description = "Campo e direção de ordenação", example = "id,asc")
            }
    )
    @GetMapping("/paginado")
    public ResponseEntity<Page<Cliente>> listarPaginado(
            @ParameterObject
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        // Lista de campos permitidos para ordenação
        List<String> camposPermitidos = List.of("id", "nome", "idade");

        // Validação do sort
        pageable.getSort().forEach(order -> {
            String campo = order.getProperty()
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\"", "");
            if (!camposPermitidos.contains(campo)) {
                throw new IllegalArgumentException("Campo de ordenação inválido: " + campo);
            }
        });

        // Se passou na validação, executa a consulta
        return ResponseEntity.ok(clienteService.listarPaginado(pageable));
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Operation(summary = "Buscar clientes por nome")
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(clienteService.buscarPorNome(nome));
    }

    @Operation(summary = "Buscar clientes por cidade")
    @GetMapping("/cidade")
    public ResponseEntity<List<Cliente>> buscarPorCidade(@RequestParam String cidade) {
        return ResponseEntity.ok(clienteService.buscarPorCidade(cidade));
    }

    @Operation(summary = "Buscar clientes por estado")
    @GetMapping("/estado")
    public ResponseEntity<List<Cliente>> buscarPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(clienteService.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar clientes por faixa etária")
    @GetMapping("/faixa-etaria")
    public ResponseEntity<List<Cliente>> buscarPorFaixaEtaria(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return ResponseEntity.ok(clienteService.buscarPorIdadeEntre(min, max));
    }

    @Operation(summary = "Criar novo cliente")
    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        Cliente novo = clienteService.criar(cliente);
        URI location = URI.create("/clientes/" + novo.getId());
        return ResponseEntity.created(location).body(novo);
    }

    @Operation(summary = "Atualizar cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.atualizar(id, cliente));
    }

    @Operation(summary = "Remover cliente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
