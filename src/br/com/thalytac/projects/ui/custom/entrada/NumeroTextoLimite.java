package br.com.thalytac.projects.ui.custom.entrada;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;

import static java.util.Objects.isNull;

public class NumeroTextoLimite extends PlainDocument {
    private final List<String> numeros = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");

    @Override
    public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
        if (isNull(str) || (!numeros.contains(str))) return;

        if (getLength() + str.length() <= 1) {
            super.insertString(offs, str, a);
        }
    }
}
