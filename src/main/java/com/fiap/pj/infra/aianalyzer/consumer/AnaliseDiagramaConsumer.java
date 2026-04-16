package com.fiap.pj.infra.aianalyzer.consumer;


import com.fiap.pj.core.aianalyzer.app.usecase.ProcessarAnaliseUseCase;
import com.fiap.pj.core.aianalyzer.app.usecase.command.ProcessarAnaliseCommand;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaSolicitadaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AnaliseDiagramaConsumer {

    private final ProcessarAnaliseUseCase processarAnaliseUseCase;

    public AnaliseDiagramaConsumer(ProcessarAnaliseUseCase processarAnaliseUseCase) {
        this.processarAnaliseUseCase = processarAnaliseUseCase;
    }

    @RabbitListener(queues = "${broker.queue.analise.solicitada}")
    public void receiveMessage(AnaliseDiagramaSolicitadaEvent message) {
        log.info("Mensagem de solicitar analise diagrama recebida. Identificador Analise Diagrama: {}", message.id());
        var cmd = new ProcessarAnaliseCommand(message.id());
        this.processarAnaliseUseCase.handle(cmd);
    }
}