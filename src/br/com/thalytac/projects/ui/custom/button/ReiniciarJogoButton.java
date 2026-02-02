package br.com.thalytac.projects.ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ReiniciarJogoButton extends JButton {
    public ReiniciarJogoButton(final ActionListener acao) {
        this.setText("Reiniciar jogo");
        this.addActionListener(acao);
    }
}