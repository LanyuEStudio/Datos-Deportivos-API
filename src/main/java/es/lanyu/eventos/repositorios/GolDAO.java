package es.lanyu.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="goles", collectionResourceRel="goles", itemResourceRel="gol")
public interface GolDAO extends JpaRepository<GolConId, Long> {

}