package com.fiap.pj.infra.aianalyzer.gateways;

import com.fiap.pj.core.aianalyzer.app.gateways.ArquivoStorageGateway;
import com.fiap.pj.core.aianalyzer.domain.exception.AnaliseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.UUID;

@Slf4j
@Component
public class ArquivoStorageGatewayImpl implements ArquivoStorageGateway {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public ArquivoStorageGatewayImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public byte[] download(UUID id) {
        var key = buildKey(id);
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
            log.info("Arquivo [{}] baixado da S3 com sucesso", key);
            return response.asByteArray();
        } catch (S3Exception ex) {
            throw new AnaliseException(
                    "DOWNLOAD_ARQUIVO_S3_ERROR",
                    "Falha ao baixar arquivo da S3. key=" + key,
                    ex
            );
        }
    }

    private String buildKey(UUID id) {
        return "diagramas/" + id;
    }
}

