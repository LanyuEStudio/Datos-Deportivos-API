package es.lanyu.participante.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import es.lanyu.participante.Participante;

//@Repository
@RepositoryRestResource(path="participantes",
                        itemResourceRel="participante",
                        collectionResourceRel="participantes")
public interface ParticipanteDAO extends JpaRepository<Participante, String> {

//    @RestResource(path="nombre")
//    List<Participante> findByNombreIgnoreCaseContaining(final String txt);

    @RestResource(path="nombre")
    // Al usar parametros hay que asegurarse de que se puedan usar como named parameters (sin guiones por ejemplo)
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.named-parameters
    // o de lo contrario solo podran referenciarse por el indice
    // https://www.baeldung.com/spring-data-jpa-query#indexed-query-parameters
    @Query("SELECT p FROM Participante p WHERE UPPER(p.nombre) LIKE UPPER(concat('%', ?1, '%'))")
    List<Participante> buscarConNombre(@Param("txt-que-contiene") String txt);
    
    @RestResource(exported=false)
    void deleteById(String id);

    @RestResource(exported=false)
    void delete(Participante entity);

    // Mejor ponerselo a todo lo que tenga que aplicarse
//    void deleteAll(Iterable<? extends Participante> entities);
//    ...

}
