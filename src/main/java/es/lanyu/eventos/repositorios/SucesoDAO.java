package es.lanyu.eventos.repositorios;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

//@Repository
@RepositoryRestResource(path="sucesos",
//                        exported=false,
                        itemResourceRel="suceso",
                        collectionResourceRel="sucesos")
public interface SucesoDAO extends JpaRepository<SucesoConId, Long> {
	
	@RestResource(path="participante")
	List<SucesoConId> findByIdParticipante(String idParticipante);
	
	@RestResource(path="entre-fechas")
	List<SucesoConId> findByTemporalBetween(Instant comienzo, Instant fin);
	
	@RestResource(exported=false)
	List<SucesoConId> findByIdParticipanteAndTemporalBetween(String idParticipante, Instant comienzo, Instant fin);
	
	@RestResource(path="despues-de")
	List<SucesoConId> findByTemporalAfter(@Param("comienzo") Instant instant);
	
}
