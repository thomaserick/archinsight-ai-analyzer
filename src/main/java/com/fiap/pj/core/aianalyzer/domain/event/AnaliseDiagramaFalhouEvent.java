package com.fiap.pj.core.aianalyzer.domain.event;

import java.util.UUID;

public record AnaliseDiagramaFalhouEvent(UUID id, String motivo) {
}
