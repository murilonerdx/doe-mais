package com.murilonerdx.doemais.exceptions;

public class DataIntegretyException extends RuntimeException{
    private String nomeDoAtributo;

    public DataIntegretyException(String nomeDoAtributo) {
        this.nomeDoAtributo = nomeDoAtributo;
    }

    @Override
    public String getMessage() {
        return nomeDoAtributo; // Mensagem
    }
}
