package es.lanyu.eventos.repositorios;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

	public void setIdParticipante(String idParticipante){
		this.idParticipante = idParticipante;
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
