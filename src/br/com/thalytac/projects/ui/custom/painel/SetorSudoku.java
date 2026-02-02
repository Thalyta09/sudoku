package br.com.thalytac.projects.ui.custom.painel;

import br.com.thalytac.projects.ui.custom.entrada.NumeroTexto;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.util.List;

import static java.awt.Color.black;

public class SetorSudoku extends JPanel {
    public SetorSudoku(final List<NumeroTexto> camposTexto) {
        var dimensao = new Dimension(170,170);

        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);

        camposTexto.forEach(this::add);
    }

}