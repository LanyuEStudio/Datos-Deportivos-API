package es.lanyu.eventos.rest;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;
import es.lanyu.comun.suceso.TarjetaImpl.TipoTarjeta;
import es.lanyu.eventos.repositorios.GolConId;
import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.PartidoDAO;
import es.lanyu.eventos.repositorios.SucesoConId;
import es.lanyu.eventos.repositorios.TarjetaConId;
import es.lanyu.participante.Participante;

@JsonComponent // Registra la clase/clases internas con (de)serializadores
// Ver https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-json-components
public class JsonDeserializers {
	private static Logger log = LoggerFactory.getLogger(JsonDeserializers.class);
	
	public static ServicioEntidad servicioEntidad;
	public static PartidoDAO partidoDAO;

	@Autowired
	public void init(ServicioEntidad servicioEntidad, PartidoDAO partidoDAO) {
		JsonDeserializers.servicioEntidad = servicioEntidad;
		JsonDeserializers.partidoDAO = partidoDAO;
	}

	@SuppressWarnings("serial")
	public static class JsonSucesoSerializer<T extends SucesoConId> extends StdDeserializer<T> {

        public JsonSucesoSerializer() {
			this((Class<T>) SucesoConId.class);
		}

		public JsonSucesoSerializer(Class<T> vc) {
			super(vc);
		}

        @Override
		public T deserialize(JsonParser jsonParser, DeserializationContext context)
				throws IOException, JsonProcessingException {
			return deserializarSuceso(jsonParser, (Class<T>) handledType());
		}

		protected T deserializarSuceso(JsonParser jsonParser, Class<T> tipo)
				throws IOException, JsonProcessingException {
			T sucesoDeserializado;
			try {
				// Variable intermedia final para los lambdas
				// El constructor vendra marcado por el tipo a construir y el parametro indicado
				T suceso = tipo.getConstructor(ServicioEntidad.class).newInstance(servicioEntidad);
				JsonNode nodo = jsonParser.getCodec().readTree(jsonParser);
				// Podemos usar optional para seguir ejecutando o no si no hay
				// campo
				Optional.ofNullable(nodo.get("timestamp")).ifPresent(n -> suceso.setTimestamp(n.asLong()));
				Optional.ofNullable(nodo.get("idParticipante")).ifPresent(n -> suceso.setIdParticipante(n.asText()));
				Optional.ofNullable(nodo.get("partido")).ifPresent(n -> {
					String idPartido = n.asText();
					idPartido = idPartido.substring(idPartido.lastIndexOf("/") + 1, idPartido.length());
					suceso.setPartido(partidoDAO.getReferenceById(Long.parseLong(idPartido)));
				});
				postDeserializarSuceso(nodo, suceso);

				sucesoDeserializado = suceso;
			} catch (Exception e) {
				e.printStackTrace();
				sucesoDeserializado = null;
			}
			
			return sucesoDeserializado;
		}

		// Callback para completar la deserializacion
		protected T postDeserializarSuceso(JsonNode nodo, T sucesoDeserializado) {
			return sucesoDeserializado;
		}
		
	}

	@SuppressWarnings("serial")
	// Basta con devolver el tipo a construir
	public static class JsonGolSerializer extends JsonSucesoSerializer<GolConId> {
		@Override public Class<?> handledType() { return GolConId.class; }
	}

	@SuppressWarnings("serial")
	public static class JsonTarjetaSerializer extends JsonSucesoSerializer<TarjetaConId> {
		@Override public Class<?> handledType() { return TarjetaConId.class; }

		@Override
		protected TarjetaConId postDeserializarSuceso(JsonNode nodo, TarjetaConId sucesoDeserializado) {
			Optional.ofNullable(nodo.get("tipoTarjeta"))
					.ifPresent(n -> sucesoDeserializado.setTipoTarjeta(TipoTarjeta.valueOf(n.asText())));
			return sucesoDeserializado;
		}

	}
	
	@SuppressWarnings("serial")
	public static class JsonPartidoSerializer extends StdDeserializer<PartidoConId> {

		public JsonPartidoSerializer() {
			this(PartidoConId.class);
		}

		public JsonPartidoSerializer(Class<PartidoConId> vc) {
			super(vc);
		}

		@Override
		public PartidoConId deserialize(JsonParser jsonParser, DeserializationContext context)
				throws IOException, JsonProcessingException {
			PartidoConId partidoDeserializado;
			try {
				String[] participantes = new String[2];
				JsonNode nodo = jsonParser.getCodec().readTree(jsonParser);
				Optional.ofNullable(nodo.get("idLocal")).ifPresent(n -> participantes[0] = n.asText());
				Optional.ofNullable(nodo.get("idVisitante")).ifPresent(n -> participantes[1] = n.asText());
				PartidoConId partido = new PartidoConId(
						servicioEntidad.getIdentificable(Participante.class, participantes[0]),
						servicioEntidad.getIdentificable(Participante.class, participantes[1]));
				Optional.ofNullable(nodo.get("timestamp")).ifPresent(n -> partido.setTimestamp(n.asLong()));
				partido.setServicioEntidad(servicioEntidad);

				partidoDeserializado = partido;
			} catch (Exception e) {
				e.printStackTrace();
				partidoDeserializado = null;
			}

			log.trace("Deserializado Partido: " + partidoDeserializado);
			
			return partidoDeserializado;
		}
		
	}

}