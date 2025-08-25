package com.dio.digitalInnovation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ViaCepFallbackFactory implements FallbackFactory<ViaCepService> {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepFallbackFactory.class);

    @Override
    public ViaCepService create(Throwable cause) {
        logger.error("Erro ao chamar ViaCEP: {}", cause.getMessage());
        return cep -> {
            logger.warn("Fallback ativado para o CEP: {}", cep);
            throw new ViaCepIndisponivelException("Serviço ViaCEP indisponível. Tente novamente mais tarde.");
        };
    }
}
