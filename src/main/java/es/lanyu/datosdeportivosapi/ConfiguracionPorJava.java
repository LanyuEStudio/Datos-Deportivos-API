package es.lanyu.datosdeportivosapi;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.lanyu.comun.evento.Partido;
import es.lanyu.comun.suceso.Gol;
import es.lanyu.comun.suceso.Suceso;
import es.lanyu.eventos.repositorios.SucesoConId;
import es.lanyu.eventos.rest.MixIns;
import es.lanyu.participante.Participante;

@Configuration
@PropertySource({ "classpath:config/rest.properties", "classpath:config/jackson.properties" })
public class ConfiguracionPorJava {
	
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