package com.fiap.pj.core.aianalyzer.app;

import com.fiap.pj.core.aianalyzer.app.gateways.ArquivoStorageGateway;
import com.fiap.pj.core.aianalyzer.app.gateways.PublisherGateway;
import com.fiap.pj.core.aianalyzer.app.usecase.command.ProcessarAnaliseCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessarAnaliseUseCaseImplTest {

    @Mock
    private ArquivoStorageGateway arquivoStorageGateway;

    @Mock
    private PublisherGateway publisherGateway;

    @InjectMocks
    private ProcessarAnaliseUseCaseImpl processarAnaliseUseCase;

    @Test
    void deveBaixarArquivoDaS3EPublicarConclusao() {
        UUID analiseId = UUID.randomUUID();
        when(arquivoStorageGateway.download(analiseId)).thenReturn(new byte[]{1, 2, 3});
        processarAnaliseUseCase.handle(new ProcessarAnaliseCommand(analiseId));
        verify(arquivoStorageGateway, times(1)).download(analiseId);
        verify(publisherGateway, times(1)).dispatch(any());
    }
}

