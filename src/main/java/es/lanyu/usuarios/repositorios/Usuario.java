package es.lanyu.usuarios.repositorios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
//	@GeneratedValue
//	int id;
	
	String nombre;
	
	String correo;
	
	public Usuario() {}
	
	public Usuario(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", correo=" + correo + "]";
	}
	
	
}
