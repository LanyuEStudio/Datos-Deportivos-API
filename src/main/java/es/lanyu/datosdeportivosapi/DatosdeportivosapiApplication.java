package es.lanyu.datosdeportivosapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.PartidoDAO;
import es.lanyu.participante.Participante;
import es.lanyu.participante.repositorios.ParticipanteDAO;

@SpringBootApplication
//@PropertySource({"otro.properties"})
@ImportResource({"classpath:config/jpa-config.xml"})
//@EnableJpaRepositories("${es.lanyu.jpa-package}")// usando propiedad, tambien se puede usar una clase
//@EntityScan("es.lanyu.usuarios.repositorios")// o definirlo literal. ambos admiten varios valores
@Import(ConfiguracionPorJava.class)
public class DatosdeportivosapiApplication {
	private static final Logger log = LoggerFactory.getLogger(DatosdeportivosapiApplication.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(DatosdeportivosapiApplication.class, args);
		
		// Dejo esto por si quisiera cargarse otra vez los Participantes
		// Elimino el resto para usar Postman para pruebas
//		ObjectMapper mapper = context.getBean(ObjectMapper.class);
//		ParticipanteDAO participanteDAO = context.getBean(ParticipanteDAO.class);
//		cargarParticipanteDesdeArchivo("src/main/resources/participantes.json", mapper, participanteDAO);
//		List<Participante> participantes = participanteDAO.findAll();
//		participantes.stream().map(Participante::toString).forEach(log::trace);
		
		// Listo los partidos en consola
//		PartidoDAO partidoDAO = context.getBean(PartidoDAO.class);
//		partidoDAO.findAll().stream().map(PartidoConId::toString).forEach(log::trace);
		
//		partidoDAO.getEventosConParticipanteConTexto("m").stream().map(PartidoConId::toString).forEach(log::trace);
		// Ya no cierro la aplicacion
//		context.close();
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

}
