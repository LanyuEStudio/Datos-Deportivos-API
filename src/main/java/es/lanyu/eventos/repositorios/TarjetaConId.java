package es.lanyu.eventos.repositorios;

import es.lanyu.comun.suceso.Tarjeta;
import es.lanyu.comun.suceso.TarjetaImpl.TipoTarjeta;

public class TarjetaConId extends SucesoConId implements Tarjeta {
	
	private TipoTarjeta tipoTarjeta;

	@Override
	public TipoTarjeta getTipoTarjeta() {
		return tipoTarjeta;
	}
	
}