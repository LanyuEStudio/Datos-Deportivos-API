package es.lanyu.eventos.repositorios;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import es.lanyu.comun.suceso.Gol;
import es.lanyu.participante.Participante;

@Entity
@Access(value=AccessType.FIELD)
@DiscriminatorValue("G")
public class GolConId extends SucesoConId implements Gol {
	
	@Override
	public Participante getEquipoAnotador() {
		return getParticipante();
	}
	
}