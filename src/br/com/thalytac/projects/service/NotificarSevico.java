package br.com.thalytac.projects.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.thalytac.projects.service.EventoEnum.LIMPAR_ESPACO;

public class NotificarSevico {
    private final Map<EventoEnum, List<EventoListener>> listeners = new HashMap<>(){{
        put(LIMPAR_ESPACO, new ArrayList<>());
    }};

    public void assinar(final EventoEnum tipoEvento, EventoListener listener) {
        var selecionado = listeners.get(tipoEvento);
        selecionado.add(listener);
    }

    public void notifica(final EventoEnum tipoEvento) {
        listeners.get(tipoEvento).forEach(l -> l.update(tipoEvento));
    }
}