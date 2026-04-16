package com.fiap.pj.infra.config;


import com.fiap.pj.core.aianalyzer.app.ProcessarAnaliseUseCaseImpl;
import com.fiap.pj.core.aianalyzer.app.gateways.ArquivoStorageGateway;
import com.fiap.pj.core.aianalyzer.app.gateways.PublisherGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnaliseConfig {

    @Bean
    ProcessarAnaliseUseCaseImpl processarAnaliseUseCase(
            ArquivoStorageGateway arquivoStorageGateway,
            PublisherGateway publisherGateway
    ) {
        return new ProcessarAnaliseUseCaseImpl(arquivoStorageGateway, publisherGateway);
    }


}
