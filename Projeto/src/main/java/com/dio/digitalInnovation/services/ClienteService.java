package com.dio.digitalInnovation.services;

import com.dio.digitalInnovation.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    List<Cliente> listarTodos();
    Page<Cliente> listarPaginado(Pageable pageable);
    Cliente buscarPorId(Long id);
    List<Cliente> buscarPorNome(String nome);
    List<Cliente> buscarPorCidade(String cidade);
    List<Cliente> buscarPorEstado(String estado);
    List<Cliente> buscarPorIdadeEntre(Integer min, Integer max);
    Cliente criar(Cliente cliente);
    Cliente atualizar(Long id, Cliente cliente);
    void remover(Long id);
}
