package br.com.thalytac.projects.model;

public class Espaco {

    private Integer atual;
    private final int experado;
    private final boolean fixo;

    public Espaco(int experado, boolean fixo) {
        this.experado = experado;
        this.fixo = fixo;
        if (fixo) {
            atual = experado;
        }
    }

    public Integer getAtual() {
        return atual;
    }

    public void setAtual(Integer atual) {
        if (fixo) return;
        this.atual = atual;
    }

    public void limparEspaco() {
        setAtual(null);
    }

    public int getExperado() {
        return experado;
    }

    public boolean isFixo() {
        return fixo;
    }

}