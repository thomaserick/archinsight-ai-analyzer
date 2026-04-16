package com.fiap.pj.core.aianalyzer.app;

import com.fiap.pj.core.aianalyzer.app.gateways.ArquivoStorageGateway;
import com.fiap.pj.core.aianalyzer.app.gateways.PublisherGateway;
import com.fiap.pj.core.aianalyzer.app.usecase.ProcessarAnaliseUseCase;
import com.fiap.pj.core.aianalyzer.app.usecase.command.ProcessarAnaliseCommand;
import com.fiap.pj.core.aianalyzer.domain.FalhaAnalise;
import com.fiap.pj.core.aianalyzer.domain.RelatorioAnalise;
import com.fiap.pj.core.aianalyzer.domain.exception.AnaliseException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ProcessarAnaliseUseCaseImpl implements ProcessarAnaliseUseCase {

    private final ArquivoStorageGateway arquivoStorageGateway;

    private final PublisherGateway eventPublisher;


    public ProcessarAnaliseUseCaseImpl(
            ArquivoStorageGateway arquivoStorageGateway,
            PublisherGateway eventPublisher
    ) {
        this.arquivoStorageGateway = arquivoStorageGateway;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Processa um comando de analise de diagrama.
     *
     * @param cmd comando contendo os dados da analise de diagrama a ser processada
     */
    @Override
    public void handle(ProcessarAnaliseCommand cmd) {
        log.info("Iniciando processamento analise de diagrama");

        try {
            processarAnaliseAI(cmd.id());
            eventPublisher.dispatch(new RelatorioAnalise(cmd.id()));
            log.info("Analise de Diagrama concluida");

        } catch (AnaliseException ex) {
            log.error("Erro ao processar analise de diagrama", ex);
            eventPublisher.dispatch(new FalhaAnalise(cmd.id(), ex.getMessage()));
        }

    }

    private void processarAnaliseAI(UUID id) {
        byte[] arquivoDiagrama = arquivoStorageGateway.download(id);

        //criar o processamento da Analise da AI
        // Gerar o relatorio e fazer upload do relatorio na S3 em uma pasta ex: relatorios/id (identificador analise)


    }


}