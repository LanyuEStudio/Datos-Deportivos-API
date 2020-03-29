package es.lanyu.eventos.repositorios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.lanyu.commons.reflect.ReflectUtils;
import es.lanyu.comun.evento.Partido;
import es.lanyu.participante.Participante;

//@Entity(name="PARTIDOS")
public class PartidoConId extends Partido {

//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	public Long getId() {
		return id;
	}
	
	// Establece la relacion en los dos sentidos
	public void addSucesoConId(SucesoConId suceso) {
		super.addSuceso(suceso);
		suceso.setPartido(this);
	}
	
	// Solo para tener acceso a los campos idLocal e idVisitante
    public String getIdLocal() throws IllegalArgumentException, IllegalAccessException{
    	return ReflectUtils.getCampo(Partido.class, "idLocal", true).get(this).toString();
    }
    public String getIdVisitante() throws IllegalArgumentException, IllegalAccessException{
    	return ReflectUtils.getCampo(Partido.class, "idVisitante", true).get(this).toString();
    }
	
	public PartidoConId(){
		super();
	}

	public PartidoConId(Participante local, Participante visitante){
		super(local, visitante);
	}

	@Override
	// toString provisional para evitar null al buscar ServicioEntidad
	public String toString() {
		String string;
		try {
			string = getIdLocal() + " vs " + getIdVisitante() + " => " + getSucesos();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			string = "Participantes N/D => " + getSucesos();
		}
		return string;
//		return "#" + id + " => " + detallesDelPartido();
	}
	
}
