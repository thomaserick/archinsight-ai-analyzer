package com.fiap.pj.core.aianalyzer.app.gateways;

import java.util.UUID;

public interface ArquivoStorageGateway {

    byte[] download(UUID objectKey);
}

