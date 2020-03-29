package es.lanyu.eventos.repositorios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.lanyu.comun.suceso.SucesoImpl;

//@Entity(name="SUCESOS")
public class SucesoConId extends SucesoImpl {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
//	@ManyToOne
//	@JoinColumn(name="ID_PARTIDO", referencedColumnName="ID")
	@JsonIgnore
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

	public String getIdParticipante(){
		return idParticipante;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ " #" + getId() + ", participante: " + getIdParticipante()
				+ " fecha=" + getTemporal();
	}
	
}
