package br.com.thalytac.projects.ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class FinalizarJogoButton extends JButton {
    public FinalizarJogoButton(final ActionListener acao) {
        this.setText("Concluir");
        this.addActionListener(acao);
    }
}