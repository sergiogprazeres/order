package com.ava.api.order.domain.enums;

public enum BusinessErrorEnum {
    RESULT_NOT_FOUND(001, "Nenhum resultado da consulta foi encontrado."),
	CONSULT_ERROR(002, "Ocorreu um erro ao efetuar a consulta."),
	SAVE_ERROR(003, "Ocorreu um erro ao salvar o pedido.");

    private final int code;
    private final String description;

    BusinessErrorEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    
    public String getErrorMessage() {
        return code + " - " + description;
    }
}

