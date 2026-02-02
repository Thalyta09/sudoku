package br.com.thalytac.projects.service;

import br.com.thalytac.projects.model.Espaco;
import br.com.thalytac.projects.model.StatusJogoEnum;
import br.com.thalytac.projects.model.Tabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabuleiroService {
    private final static int TABULEIRO_LIMITE = 9;
    private final Tabuleiro tabuleiro;

    public TabuleiroService(final Map<String, String> configJogo) {
        this.tabuleiro = new Tabuleiro(iniciarTabuleiro(configJogo));
    }

    public List<List<Espaco>> getEspacos() {
        return tabuleiro.getEspacos();
    }

    public void reiniciar() {
        tabuleiro.reiniciar();
    }

    public boolean existeErros() {
        return tabuleiro.existeErros();
    }

    public StatusJogoEnum getStatus() {
        return tabuleiro.getStatus();
    }

    public boolean jogoFinalizado() {
        return tabuleiro.jogoFinalizou();
    }

    private List<List<Espaco>> iniciarTabuleiro(final Map<String, String> configJogo) {
        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < TABULEIRO_LIMITE; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < TABULEIRO_LIMITE; j++) {
                var posicaoConfig = configJogo.get("%s,%s".formatted(i,j));
                var experado = Integer.parseInt(posicaoConfig.split(",")[0]);
                var fixo = Boolean.parseBoolean(posicaoConfig.split(",")[1]);
                var espacoAtual = new Espaco(experado, fixo);
                espacos.get(i).add(espacoAtual);
            }
        }

        return espacos;
    }
}