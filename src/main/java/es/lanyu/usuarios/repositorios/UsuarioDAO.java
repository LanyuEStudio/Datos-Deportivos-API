package es.lanyu.usuarios.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, String> {

	List<Usuario> findByCorreoContaining(String txt);
	
}
