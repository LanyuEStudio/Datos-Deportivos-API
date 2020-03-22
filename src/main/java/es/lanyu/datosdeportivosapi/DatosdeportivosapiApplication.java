package es.lanyu.datosdeportivosapi;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import es.lanyu.usuarios.repositorios.Usuario;
import es.lanyu.usuarios.repositorios.UsuarioDAO;

@SpringBootApplication
//@PropertySource({"otro.properties"})
@ImportResource({"classpath:config/jpa-config.xml"})
//@EnableJpaRepositories("${es.lanyu.jpa-package}")// usando propiedad, tambien se puede usar una clase
//@EntityScan("es.lanyu.usuarios.repositorios")// o definirlo literal. ambos admiten varios valores
public class DatosdeportivosapiApplication {
	private static final Logger log = LoggerFactory.getLogger(DatosdeportivosapiApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(DatosdeportivosapiApplication.class, args);
		
		UsuarioDAO usuarioDAO = context.getBean(UsuarioDAO.class);
		usuarioDAO.save(generaUsuario());
		List<Usuario> usuarios = usuarioDAO.findByCorreoContaining("5");
		usuarios.stream().map(Usuario::toString).forEach(log::info);
		
		context.close();
	}
	
	static Usuario generaUsuario() {
		int numero = 10000;
		String usuario = "user" + ThreadLocalRandom.current().nextInt(numero, numero*20);
		return new Usuario(usuario, usuario + "@mail.com");
	}

}
