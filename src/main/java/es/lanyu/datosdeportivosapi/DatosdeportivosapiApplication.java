package es.lanyu.datosdeportivosapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.PartidoDAO;
import es.lanyu.eventos.repositorios.SucesoConId;
import es.lanyu.eventos.repositorios.SucesoDAO;
import es.lanyu.participante.Participante;
import es.lanyu.participante.repositorios.ParticipanteDAO;

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
		
		ObjectMapper mapper = context.getBean(ObjectMapper.class);
		mapper.addMixIn(Participante.class, MixInParticipantes.class);
		ParticipanteDAO participanteDAO = context.getBean(ParticipanteDAO.class);
//		cargarParticipanteDesdeArchivo("src/main/resources/participantes.json", mapper, participanteDAO);
//		List<Participante> participantes = participanteDAO.findByNombreContaining("Real");
		List<Participante> participantes = participanteDAO.findAll();
		participantes.stream().map(Participante::toString).forEach(log::trace);
		
		PartidoConId partido = new PartidoConId(getParticipanteRandom(participantes), getParticipanteRandom(participantes));
		PartidoDAO partidoDAO = context.getBean(PartidoDAO.class);
		partido.addSucesoConId(getSucesoRandom(partido));
		partidoDAO.save(partido);
		SucesoDAO sucesoDAO = context.getBean(SucesoDAO.class);
		sucesoDAO.saveAll(partido.getSucesos().stream().map(s -> (SucesoConId)s).collect(Collectors.toList()));
		partidoDAO.findAll().stream().map(PartidoConId::toString).forEach(log::trace);
		
		context.close();
	}
	
	static Participante getParticipanteRandom(List<Participante> participantes) {
		int index = ThreadLocalRandom.current().nextInt(0, participantes.size());
		return participantes.get(index);
	}
	
	static SucesoConId getSucesoRandom(PartidoConId partido) {
		SucesoConId suceso = new SucesoConId();
		int index = ThreadLocalRandom.current().nextInt(0, partido.getParticipantes().size());
		suceso.setParticipante(index == 0 ? partido.getLocal() : partido.getVisitante());
		return suceso;
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

}
