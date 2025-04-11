package es.lanyu.eventos.repositorios;

import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import es.lanyu.commons.reflect.ReflectUtils;
import es.lanyu.comun.evento.Partido;
import es.lanyu.comun.suceso.Suceso;
import es.lanyu.participante.Participante;

//@Entity(name="PARTIDOS")
public class PartidoConId extends Partido {

//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	public Long getId() {
		return id;
	}
	
	@Override
	@OneToMany(targetEntity=SucesoConId.class)
	public Collection<Suceso> getSucesos() {
		return super.getSucesos();
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
	public String toString() {
		return "#" + id + " => " + detallesDelPartido();
	}
	
}
