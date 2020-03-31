package es.lanyu.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="tarjetas", collectionResourceRel="tarjetas", itemResourceRel="tarjetas")
public interface TarjetaDAO extends JpaRepository<TarjetaConId, Long> {

}