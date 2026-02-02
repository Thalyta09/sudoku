package br.com.thalytac.projects.ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ChecarStatusButton extends JButton {
    public ChecarStatusButton(final ActionListener acao) {
        this.setText("Verificar jogo");
        this.addActionListener(acao);
    }
}