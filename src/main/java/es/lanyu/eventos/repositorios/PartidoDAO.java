package es.lanyu.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@Repository
@RepositoryRestResource(path="partidos", // indica la parte de la URL para este recurso
                        itemResourceRel="partido", // nombre cuando se hace referencia a un partido
                        collectionResourceRel="partidos") // nombre cuando se hace referencia a varios partidos
public interface PartidoDAO extends JpaRepository<PartidoConId, Long> {
}
