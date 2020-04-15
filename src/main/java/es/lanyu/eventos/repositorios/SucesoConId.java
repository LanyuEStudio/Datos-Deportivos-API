package es.lanyu.eventos.repositorios;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.PersistenceConstructor;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;
import es.lanyu.comun.suceso.SucesoImpl;

//@Entity(name="SUCESOS")
@EntityListeners(SucesoListener.class)
public class SucesoConId extends SucesoImpl {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	@ManyToOne
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

	public String getIdParticipante(){
		return idParticipante;
	}
	
	public SucesoConId() {}
	
//	@PersistenceConstructor
	public SucesoConId(ServicioEntidad servicioEntidad) {
		this.servicioEntidad = servicioEntidad;
	}
	
	public void setServicioEntidad(ServicioEntidad servicioEntidad) {
		this.servicioEntidad = servicioEntidad;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ " #" + getId() + ", participante: " + getParticipante()
				+ " fecha=" + getTemporal();
	}
	
}
