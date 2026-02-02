package br.com.thalytac.projects.ui.custom.entrada;

import br.com.thalytac.projects.model.Espaco;
import br.com.thalytac.projects.service.EventoEnum;
import br.com.thalytac.projects.service.EventoListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.Font;

import static br.com.thalytac.projects.service.EventoEnum.LIMPAR_ESPACO;
import static java.awt.Font.PLAIN;

public class NumeroTexto extends JTextField implements EventoListener {
    private final Espaco espaco;

    public NumeroTexto(final Espaco espaco) {
        this.espaco = espaco;
        var dimensao = new Dimension(50,50);
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setDocument(new NumeroTextoLimite());
        this.setEnabled(!espaco.isFixo());

        if (espaco.isFixo()) {
            this.setText(espaco.getAtual().toString());
        }

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mudaEspaco();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mudaEspaco();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                mudaEspaco();
            }

            public void mudaEspaco() {
                if (getText().isEmpty()) {
                    espaco.limparEspaco();
                    return;
                }
                espaco.setAtual(Integer.parseInt(getText()));
            }
        });
    }

    @Override
    public void update(final EventoEnum tipoEvento) {
        if (tipoEvento.equals(LIMPAR_ESPACO) && (this.isEnabled())) {
            this.setText("");
        }
    }

}