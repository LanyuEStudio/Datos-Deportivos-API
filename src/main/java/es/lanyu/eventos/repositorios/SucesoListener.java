package es.lanyu.eventos.repositorios;

import jakarta.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;

@Component
public class SucesoListener {
	
	public static ServicioEntidad servicioEntidad;
    
	@Autowired
	public void init(ServicioEntidad servicioEntidad) {
		SucesoListener.servicioEntidad = servicioEntidad;
	}

	@PostLoad
	void setServicioEntidadEnSuceso(SucesoConId suceso) {
		suceso.setServicioEntidad(servicioEntidad);
	}

}
