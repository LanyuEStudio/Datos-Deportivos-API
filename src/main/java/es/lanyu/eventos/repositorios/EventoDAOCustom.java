package es.lanyu.eventos.repositorios;

import java.util.List;

import es.lanyu.comun.evento.Evento;

public interface EventoDAOCustom<T extends Evento> {

    List<T> getEventosConParticipanteConTexto(String txt);

}