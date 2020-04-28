package es.lanyu.eventos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

//@Repository
@RepositoryRestResource(path="partidos", // indica la parte de la URL para este recurso
                        itemResourceRel="partido", // nombre cuando se hace referencia a un partido
                        collectionResourceRel="partidos") // nombre cuando se hace referencia a varios partidos
public interface PartidoDAO extends JpaRepository<PartidoConId, Long> {

    @RestResource(path="participante")
    List<PartidoConId> findByIdLocalOrIdVisitante(@Param("idParticipante") String idLocal, @Param("idParticipante") String idVisitante);

}
