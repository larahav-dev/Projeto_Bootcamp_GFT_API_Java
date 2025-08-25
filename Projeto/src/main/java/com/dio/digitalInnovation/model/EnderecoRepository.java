package com.dio.digitalInnovation.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {

    List<Endereco> findByUf(String uf);
    List<Endereco> findByLocalidadeContainingIgnoreCase(String localidade);
}
