package es.lanyu.eventos.repositorios;

import es.lanyu.comun.suceso.Gol;
import es.lanyu.participante.Participante;

public class GolConId extends SucesoConId implements Gol {
	
	@Override
	public Participante getEquipoAnotador() {
		return getParticipante();
	}
	
}