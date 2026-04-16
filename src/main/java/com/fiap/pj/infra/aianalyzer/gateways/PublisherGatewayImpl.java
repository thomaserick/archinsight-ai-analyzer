package com.fiap.pj.infra.aianalyzer.gateways;

import com.fiap.pj.core.aianalyzer.app.gateways.PublisherGateway;
import com.fiap.pj.core.aianalyzer.domain.RelatorioAnalise;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaConcluidaEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PublisherGatewayImpl implements PublisherGateway {

    final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.analise.concluida}")
    private String routingKey;


    public PublisherGatewayImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void dispatch(RelatorioAnalise relatorioAnalise) {
        var event = new AnaliseDiagramaConcluidaEvent(relatorioAnalise.getId());
        this.rabbitTemplate.convertAndSend(routingKey, event);
    }

}