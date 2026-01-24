package br.com.thalytac.projects.model;

public enum StatusJogoEnum {
    NO_STARTED("Não iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    private final String valor;

    StatusJogoEnum(final String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}