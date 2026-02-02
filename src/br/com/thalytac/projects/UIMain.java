package br.com.thalytac.projects;

import br.com.thalytac.projects.ui.custom.tela.TelaPrincipal;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {
    public static void main(String[] args) {
        final var configJogo = Stream.of(args).collect(toMap
                (k -> k.split(";")[0], v -> v.split(";")[1]));
        var telaPrincipal = new TelaPrincipal(configJogo);
        telaPrincipal.constroiTelaPrincipal();
    }
}