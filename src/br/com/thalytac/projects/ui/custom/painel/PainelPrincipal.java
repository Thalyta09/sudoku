package br.com.thalytac.projects.ui.custom.painel;

import javax.swing.JPanel;
import java.awt.Dimension;

public class PainelPrincipal extends JPanel {
    public PainelPrincipal(final Dimension dimensao) {
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
    }
}