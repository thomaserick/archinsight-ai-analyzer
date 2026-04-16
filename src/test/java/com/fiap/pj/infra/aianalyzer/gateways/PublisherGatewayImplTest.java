package com.fiap.pj.infra.aianalyzer.gateways;

import com.fiap.pj.core.aianalyzer.domain.FalhaAnalise;
import com.fiap.pj.core.aianalyzer.domain.RelatorioAnalise;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaConcluidaEvent;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaFalhouEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PublisherGatewayImplTest {

    private static final String ROUTING_KEY_CONCLUIDA = "test.analise.concluida";
    private static final String ROUTING_KEY_FALHA = "test.analise.falhou";

    @Mock
    private RabbitTemplate rabbitTemplate;

    private PublisherGatewayImpl publisherGateway;

    @BeforeEach
    void setUp() throws Exception {
        publisherGateway = new PublisherGatewayImpl(rabbitTemplate);
        setField(publisherGateway, "routingKey", ROUTING_KEY_CONCLUIDA);
        setField(publisherGateway, "routingKeyFalha", ROUTING_KEY_FALHA);
    }

    @Test
    void deveEnviarEventoConcluidaQuandoDispatchRelatorioAnalise() {
        UUID id = UUID.randomUUID();

        publisherGateway.dispatch(new RelatorioAnalise(id));

        verify(rabbitTemplate, times(1))
                .convertAndSend(ROUTING_KEY_CONCLUIDA, new AnaliseDiagramaConcluidaEvent(id));
    }

    @Test
    void deveEnviarEventoFalhouQuandoDispatchFalhaAnalise() {
        UUID id = UUID.randomUUID();
        String motivo = "Erro no processamento";

        publisherGateway.dispatch(new FalhaAnalise(id, motivo));

        verify(rabbitTemplate, times(1))
                .convertAndSend(ROUTING_KEY_FALHA, new AnaliseDiagramaFalhouEvent(id, motivo));
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}

