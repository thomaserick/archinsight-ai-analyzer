package com.fiap.pj.infra.aianalyzer.gateways;

import com.fiap.pj.core.aianalyzer.app.gateways.PublisherGateway;
import com.fiap.pj.core.aianalyzer.domain.FalhaAnalise;
import com.fiap.pj.core.aianalyzer.domain.RelatorioAnalise;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaConcluidaEvent;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaFalhouEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PublisherGatewayImpl implements PublisherGateway {

    final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.analise.concluida}")
    private String routingKey;

    @Value("${broker.queue.analise.falhou}")
    private String routingKeyFalha;


    public PublisherGatewayImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void dispatch(RelatorioAnalise relatorioAnalise) {
        var event = new AnaliseDiagramaConcluidaEvent(relatorioAnalise.id());
        this.rabbitTemplate.convertAndSend(routingKey, event);
    }

    @Override
    public void dispatch(FalhaAnalise falhaAnalise) {
        var event = new AnaliseDiagramaFalhouEvent(falhaAnalise.id(), falhaAnalise.motivo());
        this.rabbitTemplate.convertAndSend(routingKeyFalha, event);
    }

}