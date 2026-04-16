package com.fiap.pj.core.aianalyzer.app.gateways;

import com.fiap.pj.core.aianalyzer.domain.FalhaAnalise;
import com.fiap.pj.core.aianalyzer.domain.RelatorioAnalise;

public interface PublisherGateway {

    void dispatch(RelatorioAnalise relatorioAnalise);

    void dispatch(FalhaAnalise falhaAnalise);

}
