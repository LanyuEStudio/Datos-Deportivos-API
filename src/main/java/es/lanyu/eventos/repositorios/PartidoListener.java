package es.lanyu.eventos.repositorios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;

@Component
public class PartidoListener {
	private Logger log = LoggerFactory.getLogger(PartidoListener.class);
	
	private static ServicioEntidad servicioEntidad;
	private static SucesoDAO sucesoDAO;
    
	@Autowired
	public void init(ServicioEntidad servicioEntidad, SucesoDAO sucesoDAO) {
		PartidoListener.servicioEntidad = servicioEntidad;
		PartidoListener.sucesoDAO = sucesoDAO;
	}

//	@PostLoad
	void setServicioEntidadEnPartido(PartidoConId partido) {
		partido.setServicioEntidad(servicioEntidad);
//		log.trace("@PostLoad Partido: " + partido);
	}

//	@PreRemove
	void antesDeBorrar(PartidoConId partido) {
		log.trace("@PreRemove Partido: borrando " + partido.getSucesos().size() + " sucesos");
		partido.getSucesos().forEach(s -> sucesoDAO.delete((SucesoConId) s));
	}
}
