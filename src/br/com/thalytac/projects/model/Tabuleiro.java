package br.com.thalytac.projects.model;

import java.util.Collection;
import java.util.List;

import static br.com.thalytac.projects.model.StatusJogoEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Tabuleiro {

    private final List<List<Espaco>> espacos;

    public Tabuleiro(List<List<Espaco>> espacos) {
        this.espacos = espacos;
    }

    public List<List<Espaco>> getEspacos() {
        return espacos;
    }

    public StatusJogoEnum getStatus() {
        if (espacos.stream().flatMap(Collection::stream)
                .noneMatch(s -> !s.isFixo() && nonNull(s.getAtual()))) {
            return NO_STARTED;
        }

        return espacos.stream().flatMap(Collection::stream)
                .anyMatch(s -> isNull(s.getAtual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean existeErros() {
        if (getStatus() == NO_STARTED)
            return false;

        return espacos.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getAtual()) && !s.getAtual().equals(s.getExperado()));
    }

    public boolean mudouValor(final int col, final int linha, final int valor) {
        var espaco = espacos.get(col).get(linha);
        if (espaco.isFixo())
            return false;

        espaco.setAtual(valor);
        return true;
    }

    public boolean limparValor(final int col, final int linha) {
        var espaco = espacos.get(col).get(linha);
        if (espaco.isFixo())
            return false;

        espaco.limparEspaco();
        return true;
    }

    public void reiniciar() {
        espacos.forEach(c -> c.forEach(Espaco::limparEspaco));
    }

    public boolean jogoFinalizou() {
        return !existeErros() && getStatus().equals(COMPLETE);
    }

}