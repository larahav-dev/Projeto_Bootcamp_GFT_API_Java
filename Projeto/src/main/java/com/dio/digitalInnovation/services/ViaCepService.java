package com.dio.digitalInnovation.services;

import com.dio.digitalInnovation.model.Endereco;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "viacepClient",
        url = "https://viacep.com.br/ws",
        fallbackFactory = ViaCepFallbackFactory.class
)
public interface ViaCepService {

    @Cacheable("enderecos")
    @GetMapping("/{cep}/json/")
    Endereco consultarCep(@PathVariable("cep") String cep);
}
