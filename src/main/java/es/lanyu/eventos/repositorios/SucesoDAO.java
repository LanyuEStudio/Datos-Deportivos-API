package es.lanyu.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucesoDAO extends JpaRepository<SucesoConId, Long> {
}
