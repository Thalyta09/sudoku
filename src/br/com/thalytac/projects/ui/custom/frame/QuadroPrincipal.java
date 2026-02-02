package br.com.thalytac.projects.ui.custom.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

public class QuadroPrincipal extends JFrame {
    public QuadroPrincipal(final Dimension dimensao, final JPanel painelPrincipal) {
        super("Sudoku");
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(painelPrincipal);
    }
}