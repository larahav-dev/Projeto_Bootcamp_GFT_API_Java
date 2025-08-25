package com.dio.digitalInnovation.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Ajustes: busca por cidade (localidade) e estado (uf)
    List<Cliente> findByEndereco_LocalidadeIgnoreCase(String localidade);
    List<Cliente> findByEndereco_UfIgnoreCase(String uf);

    List<Cliente> findByIdadeBetween(Integer min, Integer max);
}
