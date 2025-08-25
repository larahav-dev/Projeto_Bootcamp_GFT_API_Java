package com.dio.digitalInnovation.controller;

import com.dio.digitalInnovation.model.Cliente;
import com.dio.digitalInnovation.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Operation(summary = "Listar clientes com paginação")
    @GetMapping("/paginado")
    public ResponseEntity<Page<Cliente>> listarPaginado(Pageable pageable) {
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
