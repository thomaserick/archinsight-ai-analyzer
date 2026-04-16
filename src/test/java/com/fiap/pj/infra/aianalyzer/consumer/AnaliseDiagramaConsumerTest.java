package com.fiap.pj.infra.aianalyzer.consumer;

import com.fiap.pj.core.aianalyzer.app.usecase.ProcessarAnaliseUseCase;
import com.fiap.pj.core.aianalyzer.app.usecase.command.ProcessarAnaliseCommand;
import com.fiap.pj.core.aianalyzer.domain.event.AnaliseDiagramaSolicitadaEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AnaliseDiagramaConsumerTest {

    @Mock
    private ProcessarAnaliseUseCase processarAnaliseUseCase;

    @InjectMocks
    private AnaliseDiagramaConsumer analiseDiagramaConsumer;

    @Test
    void deveProcessarMensagemRecebida() {
        UUID analiseId = UUID.randomUUID();
        var event = new AnaliseDiagramaSolicitadaEvent(analiseId);

        analiseDiagramaConsumer.receiveMessage(event);

        verify(processarAnaliseUseCase, times(1))
                .handle(new ProcessarAnaliseCommand(analiseId));
    }
}

