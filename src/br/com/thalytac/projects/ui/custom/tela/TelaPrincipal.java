package br.com.thalytac.projects.ui.custom.tela;

import br.com.thalytac.projects.model.Espaco;
import br.com.thalytac.projects.service.NotificarSevico;
import br.com.thalytac.projects.service.TabuleiroService;
import br.com.thalytac.projects.ui.custom.button.ChecarStatusButton;
import br.com.thalytac.projects.ui.custom.button.FinalizarJogoButton;
import br.com.thalytac.projects.ui.custom.button.ReiniciarJogoButton;
import br.com.thalytac.projects.ui.custom.entrada.NumeroTexto;
import br.com.thalytac.projects.ui.custom.frame.QuadroPrincipal;
import br.com.thalytac.projects.ui.custom.painel.PainelPrincipal;
import br.com.thalytac.projects.ui.custom.painel.SetorSudoku;

import javax.swing.*;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.thalytac.projects.service.EventoEnum.LIMPAR_ESPACO;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class TelaPrincipal {
    private final static int TABULEIRO_LIMITE = 9;
    private final static Dimension dimensao = new Dimension(600,600);

    private final TabuleiroService tabuleiroService;
    private final NotificarSevico notificarSevico;

    private JButton checarStatusButton;
    private JButton finalizarJogoButton;
    private JButton reiniciarJogoButton;

    public TelaPrincipal(final Map<String, String> configJogo) {
        this.tabuleiroService = new TabuleiroService(configJogo);
        this.notificarSevico = new NotificarSevico();
    }

    public void constroiTelaPrincipal() {
        JPanel painelPrincipal = new PainelPrincipal(dimensao);
        JFrame quadroPrincipal = new QuadroPrincipal(dimensao, painelPrincipal);

        for (int l = 0; l < TABULEIRO_LIMITE; l+=3) {
            var endLinha = l+2;
            for (int c = 0; c < TABULEIRO_LIMITE; c+=3) {
                var endCol = c+2;
                var espacos = getEspacosSetor(tabuleiroService.getEspacos(), c, endCol, l, endLinha);
                JPanel setor = gerarSetor(espacos);
                painelPrincipal.add(setor);
            }
        }

        addBotaoReiniciarJogo(painelPrincipal);
        addBotaoChecarStatus(painelPrincipal);
        addBotaoFinalizarJogo(painelPrincipal);
        quadroPrincipal.revalidate();
        quadroPrincipal.repaint();
    }

    private List<Espaco> getEspacosSetor(final List<List<Espaco>> espacos,
                                         final int initCol, final int endCol,
                                         final int initLinha, final int endLinha) {
        List<Espaco> espacoSetor = new ArrayList<>();

        for (int l = initLinha; l <= endLinha; l++) {
            for (int c = initCol; c <= endCol; c++) {
                espacoSetor.add(espacos.get(c).get(l));
            }
        }

        return espacoSetor;
    }

    private JPanel gerarSetor(final List<Espaco> espacos) {
        List<NumeroTexto> campos = new ArrayList<>(espacos.stream().map(NumeroTexto::new).toList());
        campos.forEach(t -> notificarSevico.assinar(LIMPAR_ESPACO, t));
        return new SetorSudoku(campos);
    }

    private void addBotaoFinalizarJogo(final JPanel painelPrincipal) {
        finalizarJogoButton = new FinalizarJogoButton(e -> {
            if (tabuleiroService.jogoFinalizado()) {
                showMessageDialog(null, "Parabéns, você finalizou o jogo!");
                reiniciarJogoButton.setEnabled(false);
                checarStatusButton.setEnabled(false);
                finalizarJogoButton.setEnabled(false);
            } else {
                showMessageDialog(null, "Seu jogo contém erros, ajuste e tente novamente.");
            }
        });

        painelPrincipal.add(finalizarJogoButton);
    }

    private void addBotaoChecarStatus(final JPanel painelPrincipal) {
        checarStatusButton = new ChecarStatusButton(e -> {
            var existeErros = tabuleiroService.existeErros();
            var statusJogo  = tabuleiroService.getStatus();
            var mensagem = switch (statusJogo) {
                case NO_STARTED ->  "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };

            mensagem += existeErros ? " e contém erros." : " e não contém erros";
            showMessageDialog(null, mensagem);
        });

        painelPrincipal.add(TelaPrincipal.this.checarStatusButton);
    }

    private void addBotaoReiniciarJogo(JPanel painelPrincipal) {
        reiniciarJogoButton = new ReiniciarJogoButton(e -> {
            var result = showConfirmDialog(
                    null,
                    "Deseja reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );

            if (result == 0) {
                tabuleiroService.reiniciar();
                notificarSevico.notifica(LIMPAR_ESPACO);
            }
        });

        painelPrincipal.add(reiniciarJogoButton);
    }

}