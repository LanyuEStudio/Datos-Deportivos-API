package es.lanyu.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@Repository
@RepositoryRestResource(path="sucesos",
//                        exported=false,
                        itemResourceRel="suceso",
                        collectionResourceRel="sucesos")
public interface SucesoDAO extends JpaRepository<SucesoConId, Long> {
}
