package com.fiap.pj.infra.aianalyzer.gateways;

import com.fiap.pj.core.aianalyzer.domain.exception.AnaliseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArquivoStorageGatewayImplTest {

    private static final String BUCKET = "test-bucket";

    @Mock
    private S3Client s3Client;

    private ArquivoStorageGatewayImpl gateway;

    @BeforeEach
    void setUp() throws Exception {
        gateway = new ArquivoStorageGatewayImpl(s3Client);
        Field bucketField = ArquivoStorageGatewayImpl.class.getDeclaredField("bucket");
        bucketField.setAccessible(true);
        bucketField.set(gateway, BUCKET);
    }

    @Test
    void deveRetornarBytesQuandoDownloadComSucesso() {
        UUID id = UUID.randomUUID();
        byte[] conteudo = {1, 2, 3};
        ResponseBytes<GetObjectResponse> responseBytes = ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), conteudo);
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenReturn(responseBytes);

        byte[] resultado = gateway.download(id);

        assertArrayEquals(conteudo, resultado);
        verify(s3Client).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    void deveLancarAnaliseExceptionQuandoS3Falhar() {
        UUID id = UUID.randomUUID();
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class)))
                .thenThrow(S3Exception.builder().message("not found").build());

        AnaliseException ex = assertThrows(AnaliseException.class, () -> gateway.download(id));

        assertEquals("DOWNLOAD_ARQUIVO_S3_ERROR", ex.getCodigoErro());
        assertTrue(ex.getMessage().contains("diagramas/" + id));
    }
}

