package es.lanyu.eventos.repositorios;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;
import es.lanyu.comun.suceso.Tarjeta;
import es.lanyu.comun.suceso.TarjetaImpl.TipoTarjeta;

// Mapeo ORM en resources/jpa/SucesoConId.xml
public class TarjetaConId extends SucesoConId implements Tarjeta {

	private TipoTarjeta tipoTarjeta;

	@Override
	public TipoTarjeta getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public TarjetaConId() {}
	
	public TarjetaConId(ServicioEntidad servicioEntidad) {
		super(servicioEntidad);
	}
	
	@Override
	public String toString() {
		return "Tarjeta " + getTipoTarjeta()
			+ ((getActor() != null) ? " para " + getActor() : " ") + getParticipante();
	};
	
}