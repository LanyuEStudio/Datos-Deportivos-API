package es.lanyu.datosdeportivosapi;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;
import es.lanyu.commons.servicios.entidad.ServicioEntidadImpl;
import es.lanyu.comun.evento.Partido;
import es.lanyu.comun.suceso.Gol;
import es.lanyu.comun.suceso.Suceso;
import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.SucesoConId;
import es.lanyu.eventos.rest.MixIns;
import es.lanyu.eventos.rest.PartidoController;
import es.lanyu.participante.Participante;
import es.lanyu.participante.repositorios.ParticipanteDAO;

@Configuration
@PropertySource({ "classpath:config/rest.properties", "classpath:config/jackson.properties" })
@ComponentScan("es.lanyu.eventos")
public class ConfiguracionPorJava {
    
    @Bean
    // Enlace con un par de formas (version antigua) de poner links en /search: https://stackoverflow.com/a/36007306
    RepresentationModelProcessor<RepositorySearchesResource> searchLinks(RepositoryRestConfiguration config) {
        return new RepresentationModelProcessor<RepositorySearchesResource>() {

            @Override
            public RepositorySearchesResource process(RepositorySearchesResource searchResource) {
                // Esto se ejecuta para cualquier /search con lo que hay que filtrar cuando se usa
                if (searchResource.getDomainType().equals(PartidoConId.class)) {
                    try {
                        String nombreMetodo = "getPartidosConParticipanteComo";
                        // https://docs.spring.io/spring-hateoas/docs/1.0.0.M1/reference/html/#fundamentals.obtaining-links.builder.methods
                        Method method = PartidoController.class.getMethod(nombreMetodo, String.class,
                                PersistentEntityResourceAssembler.class);
                        URI uri = org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                                .linkTo(method, null, null).toUri();
                        // Lamentablemente no coge el basePath y hay que implementar esa parte
                        // Sacado de: https://github.com/spring-projects/spring-hateoas-examples/tree/master/ //
                        // spring-hateoas-and-spring-data-rest#altering-what-spring-data-rest-is-serving
                        String url = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                                config.getBasePath() + uri.getPath(), uri.getQuery(), uri.getFragment()).toString();
                        searchResource.add(new Link(url + "{?txt}", nombreMetodo));
                    } catch (NoSuchMethodException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

                return searchResource;
            }

        };
    }

	@Bean
	public ServicioEntidad getServicioEntidad(ParticipanteDAO participanteDAO){
		ServicioEntidad servicioEntidad = new ServicioEntidadImpl();
		participanteDAO.findAll()
			.forEach(p -> servicioEntidad.getGestorNombrables().addNombrable(Participante.class, p));
		
		return servicioEntidad;
	}
	
	@Bean
	// Tambien se le aplican las propiedades de jackson aunque se use new ObjectMapper()
	// porque es un bean y se configura (esto seria como su constructor)
	public ObjectMapper getObjectMapper() {

		ObjectMapper mapper = new ObjectMapper();
		// Los MixIn se pueden usar y reutilizar sobre codigo que no controlo
		mapper.addMixIn(Participante.class, MixIns.Participantes.class);
		mapper.addMixIn(Partido.class, MixIns.Partidos.class);
		// Incluido sobre las interfaces
		mapper.addMixIn(Suceso.class, MixIns.Sucesos.class);
		mapper.addMixIn(Gol.class, MixIns.Goles.class);
		
		// Aqui no se conserva toda la configuracion (se contruye)
		// Esta si porque no se sobrescribe
//		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
//		mapper.setVisibility(PropertyAccessor.GETTER, Visibility.PROTECTED_AND_PUBLIC);
		
		// Esta no se conserva porque la coge de properties
//		mapper.setSerializationInclusion(Include.ALWAYS);
		
		return mapper;
	}
	
//    @Bean
//	Otra forma de personalizar (esto seria como su metodo de configuracion)
    public Jackson2ObjectMapperBuilderCustomizer addCustomSerialization() {
        return new Jackson2ObjectMapperBuilderCustomizer() {

            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.featuresToDisable(
                        // Tanto para deserializacion como serializacion
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        SerializationFeature.FAIL_ON_EMPTY_BEANS
                    );
                // Add your customization
                // jacksonObjectMapperBuilder.featuresToEnable(...)
                // jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.);
                // jacksonObjectMapperBuilder.autoDetectGettersSetters(false);
                // jacksonObjectMapperBuilder.autoDetectFields(true);

                jacksonObjectMapperBuilder.mixIn(Participante.class, MixIns.Participantes.class);
                jacksonObjectMapperBuilder.mixIn(Partido.class, MixIns.Partidos.class);
                jacksonObjectMapperBuilder.mixIn(SucesoConId.class, MixIns.Sucesos.class);
                jacksonObjectMapperBuilder.mixIn(Gol.class, MixIns.Goles.class);

                // Aqui se aplica toda la configuracion (es un metodo)
                jacksonObjectMapperBuilder.visibility(PropertyAccessor.FIELD, Visibility.ANY);
                jacksonObjectMapperBuilder.visibility(PropertyAccessor.GETTER, Visibility.PROTECTED_AND_PUBLIC);
                jacksonObjectMapperBuilder.serializationInclusion(Include.ALWAYS);
            }
        };
    }
    
}