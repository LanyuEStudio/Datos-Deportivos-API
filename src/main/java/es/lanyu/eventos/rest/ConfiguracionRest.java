package es.lanyu.eventos.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.SucesoConId;

/**
 * Configuracion de uso generalizado para distintos proyectos Spring Data Rest.
 * Proporciona las siguientes funcionalidades:
 * <ol>
 * <li>{@link #addSearchLinks(RepositoryRestConfiguration)}: enlaza cada
 * {@code /recursos/search} automaticamente con los metodos de los controladores registrados.</li>
 * <li>{@link #corsFilter()}: permite cualquier solicitud Cross-Origin.</li>
 * </ol>
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class ConfiguracionRest {

    /**Enlaza automaticamente los links de los controladores registrados siguiendo las 
     * <a href="https://www.hijosdelspectrum.com/2020/05/codigo-util-clase-configuracionrest.html">instrucciones </a>
     * @param config {@link RepositoryRestConfiguration} para recuperar al {@code basePath}
     * @return el bean del tipo {@code RepresentationModelProcessor<RepositorySearchesResource>}
     */
    @Bean
    RepresentationModelProcessor<RepositorySearchesResource> addSearchLinks(RepositoryRestConfiguration config) {
        Map<Class<?>, Class<?>> controllersRegistrados = new HashMap<>();
        controllersRegistrados.put(PartidoConId.class, PartidoController.class);
        controllersRegistrados.put(SucesoConId.class, SucesoController.class);

        return new RepresentationModelProcessor<RepositorySearchesResource>() {

            @Override
            public RepositorySearchesResource process(RepositorySearchesResource searchResource) {
                if (controllersRegistrados.containsKey(searchResource.getDomainType())) {
                    Method[] metodos = controllersRegistrados.get(searchResource.getDomainType()).getDeclaredMethods();
                    for (Method m : metodos) {
                        if (!m.isAnnotationPresent(ResponseBody.class)) continue;
                        try {
                            Object[] pathVars = Stream.of(m.getParameters())
                                    .filter(p -> p.isAnnotationPresent(PathVariable.class))
                                    .map(p -> "(" + p.getName() + ")")
                                    .collect(Collectors.toList()).toArray();
                            URI uri = linkTo(m, pathVars).toUri();
                            String path = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                                    config.getBasePath() + uri.getPath(), uri.getQuery(), uri.getFragment())
                                    .toString().replace("(", "{").replace(")", "}");
                            String requestParams = Stream.of(m.getParameters())
                                    .filter(p -> p.isAnnotationPresent(RequestParam.class))
                                    .map(Parameter::getName)
                                    .collect(Collectors.joining(","));
                            searchResource.add(new Link(path + "{?" + requestParams + "}", m.getName()));
                        } catch (URISyntaxException e) { e.printStackTrace(); }
                    }
                }

                return searchResource;
            }

        };
    }

    /**
     * Ver tambien <a href=
     * "https://docs.spring.io/spring-data/rest/docs/current/reference/html/#customizing-sdr.configuring-cors">
     * Configuring CORS</a> para configuracion Data Rest adicional heredada con
     * {@link org.springframework.web.bind.annotation.CrossOrigin}.
     * 
     * @return bean del tipo {@link CorsFilter} permitiendo cualquier solicitud
     */
    @Bean
    CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}