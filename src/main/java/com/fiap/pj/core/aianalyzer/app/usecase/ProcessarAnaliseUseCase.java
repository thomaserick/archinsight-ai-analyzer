package com.fiap.pj.core.aianalyzer.app.usecase;

import com.fiap.pj.core.aianalyzer.app.usecase.command.ProcessarAnaliseCommand;

public interface ProcessarAnaliseUseCase {

    void handle(ProcessarAnaliseCommand cmd);
}
