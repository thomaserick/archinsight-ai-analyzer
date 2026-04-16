package com.fiap.pj.core.aianalyzer.domain.exception;

import lombok.Getter;

/**
 * Exceção específica para erros no processamento de analise diagrama.
 */
@Getter
public class AnaliseException extends RuntimeException {

    private final String codigoErro;

    public AnaliseException(String mensagem) {
        super(mensagem);
        this.codigoErro = "ANALISE_ERROR";
    }

    public AnaliseException(String codigoErro, String mensagem) {
        super(mensagem);
        this.codigoErro = codigoErro;
    }

    public AnaliseException(String codigoErro, String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.codigoErro = codigoErro;
    }

    public String getCodigoErro() {
        return codigoErro;
    }
}

