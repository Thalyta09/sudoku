package br.com.thalytac.projects;

import br.com.thalytac.projects.model.Espaco;
import br.com.thalytac.projects.model.Tabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static br.com.thalytac.projects.util.TemplateTabuleiro.TABULEIRO_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private static Tabuleiro tabuleiro;
    private final static int LIMITE_TAB = 9;

    public static void main(String[] args) {
        final var posicoes = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        //Iniciando a criação do menu do jogo
        var opcoes = -1;
        while(true) {
            System.out.println("Selecione uma das opções a seguir:");
            System.out.println("1 - Iniciar um novo jogo");
            System.out.println("2 - Incluir novo número");
            System.out.println("3 - Retirar um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Encerrar jogo");
            System.out.println("8 - Sair");

            opcoes = scanner.nextInt();

            switch (opcoes) {
                case 1 -> IniciarJogo(posicoes);
                case 2 -> IncluirNumero();
                case 3 -> RetirarNumero();
                case 4 -> ExibirJogoAtual();
                case 5 -> ExibirStatusAtual();
                case 6 -> LimparJogo();
                case 7 -> FinalizarJogo();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu.");
            }
        }
    }

    private static void IniciarJogo(final Map<String, String> posicoes) {
        if(nonNull(tabuleiro)) {
            System.out.println("O jogo já foi iniciado.");
            return;
        }

        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < LIMITE_TAB; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < LIMITE_TAB; j++) {
                var posicaoConfig = posicoes.get("%s,%s".formatted(i,j));
                var experado = Integer.parseInt(posicaoConfig.split(",")[0]);
                var fixo = Boolean.parseBoolean(posicaoConfig.split(",")[1]);
                var espacoAtual = new Espaco(experado, fixo);
                espacos.get(i).add(espacoAtual);
            }
        }

        tabuleiro = new Tabuleiro(espacos);
        System.out.println("O jogo está pronto para começar!");
    }

    private static void IncluirNumero() {
        if (isNull(tabuleiro)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        System.out.println("Informe a coluna que o número será inserido:");
        var col = ExecutarAteObterNumeroValido(0,8);
        System.out.println("Informe a linha que o número será inserido:");
        var linha = ExecutarAteObterNumeroValido(0,8);
        System.out.printf("Informe o número que irá entrar na posição[%s,%s]\n", col, linha);
        var valor = ExecutarAteObterNumeroValido(1,9);

        if (!tabuleiro.mudouValor(col, linha, valor)) {
            System.out.printf("A posição [%s,%s] tem um valor fixo.", col, linha);
        }
    }

    private static void RetirarNumero() {
        if (isNull(tabuleiro)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        System.out.println("Informe a coluna que o número será retirado:");
        var col = ExecutarAteObterNumeroValido(0,8);
        System.out.println("Informe a linha que o número será retirado:");
        var linha = ExecutarAteObterNumeroValido(0,8);

        if (!tabuleiro.limparValor(col, linha)) {
            System.out.printf("A posição [%s,%s] tem um valor fixo.", col, linha);
        }
    }

    private static void ExibirJogoAtual() {
        if (isNull(tabuleiro)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < LIMITE_TAB; i++) {
            for (var col : tabuleiro.getEspacos()) {
                args[argPos ++] = " " + ((isNull(col.get(i).getAtual())) ? " " : col.get(i).getAtual());
            }
        }

        System.out.println("O jogo se encontra na seguinte forma:");
        System.out.printf((TABULEIRO_TEMPLATE) + "\n", args);
    }

    private static void ExibirStatusAtual() {
        if (isNull(tabuleiro)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        System.out.printf("Status atual do jogo: %s\n", tabuleiro.getStatus().getValor());
        if (tabuleiro.existeErros()) {
            System.out.println("O jogo contém erros.");
        } else {
            System.out.println("O jogo não contém erros.");
        }
    }

    private static void LimparJogo() {
        if (isNull(tabuleiro)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        System.out.println("Tem certeza que deseja limpar o jogo e perder o progresso atual?");
        var confirma = scanner.next();

        if (!confirma.equalsIgnoreCase("sim") && !confirma.equalsIgnoreCase("não")) {
            System.out.println("Informe 'sim' ou 'não'");
            confirma = scanner.next();
        }

        if (confirma.equalsIgnoreCase("sim"))
            tabuleiro.reiniciar();
    }

    private static void FinalizarJogo() {
        if (isNull(tabuleiro)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        if(tabuleiro.jogoFinalizou()) {
            System.out.println("Você finalizou o jogo. Parabéns!");
            ExibirJogoAtual();
            tabuleiro = null;
        } else if (tabuleiro.existeErros()) {
            System.out.println("Esse jogo contém erros, verifique e ajuste o tabuleiro.");
        } else {
            System.out.println("Ainda há espaços sem preencher no seu tabuleiro.");
        }
    }

    private static int ExecutarAteObterNumeroValido(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }

        return current;
    }

}