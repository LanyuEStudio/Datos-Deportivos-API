package es.lanyu.eventos.repositorios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import es.lanyu.comun.suceso.SucesoImpl;

//@Entity(name="SUCESOS")
public class SucesoConId extends SucesoImpl {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
//	@ManyToOne
//	@JoinColumn(name="ID_PARTIDO", referencedColumnName="ID")
	PartidoConId partido;

	public Long getId() {
		return id;
	}

	public PartidoConId getPartido() {
		return partido;
	}

	public void setPartido(PartidoConId partido) {
		this.partido = partido;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ " #" + getId() + ", participante: " + idParticipante
				+ " fecha=" + getTemporal();
	}
	
}
