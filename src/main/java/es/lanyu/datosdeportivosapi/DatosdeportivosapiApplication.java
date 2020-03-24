package es.lanyu.datosdeportivosapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.lanyu.participante.Participante;
import es.lanyu.participante.repositorios.ParticipanteDAO;
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
		
//		UsuarioDAO usuarioDAO = context.getBean(UsuarioDAO.class);
//		usuarioDAO.save(generaUsuario());
//		List<Usuario> usuarios = usuarioDAO.findByCorreoContaining("6");
//		usuarios.stream().map(Usuario::toString).forEach(log::info);
		
		ObjectMapper mapper = context.getBean(ObjectMapper.class);
		mapper.addMixIn(Participante.class, MixInParticipantes.class);
		ParticipanteDAO participanteDAO = context.getBean(ParticipanteDAO.class);
		cargarParticipanteDesdeArchivo("src/main/resources/participantes.json", mapper, participanteDAO);
		List<Participante> participantes = participanteDAO.findByNombreContaining("Real");
		participantes.stream().map(Participante::toString).forEach(log::trace);
		
		context.close();
	}
	
	@JsonIgnoreProperties(value = { "hashCode" })
	abstract class MixInParticipantes {
		@JsonProperty("id")
		abstract String getIdentificador();
	}
	
	static void cargarParticipanteDesdeArchivo(String ruta, ObjectMapper mapper, ParticipanteDAO participanteDAO) {
		String linea = null;
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try (BufferedReader buffer = new BufferedReader(
				new InputStreamReader(new FileInputStream(ruta), "UTF-8"))) {
			Participante participante;
			while((linea = buffer.readLine()) != null) {
				if (linea.startsWith("{") && linea.endsWith("}")) {
					participante = mapper.readValue(linea, Participante.class);
					participanteDAO.save(participante);
//					log.trace("Cargado {}", participante);
				}
			}
		} catch (Exception e) {
			log.error("Error leyendo: {}", linea);
		}
	}
	
	static Usuario generaUsuario() {
		int numero = 10000;
		String usuario = "user" + ThreadLocalRandom.current().nextInt(numero, numero*20);
		return new Usuario(usuario, usuario + "@mail.com");
	}

}
