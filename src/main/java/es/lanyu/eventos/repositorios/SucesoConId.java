package es.lanyu.eventos.repositorios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import es.lanyu.comun.suceso.SucesoImpl;

//@Entity(name="SUCESOS")
public class SucesoConId extends SucesoImpl {

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ " #" + getId() + ", participante: " + idParticipante
				+ " fecha=" + getTemporal();
	}
	
}
