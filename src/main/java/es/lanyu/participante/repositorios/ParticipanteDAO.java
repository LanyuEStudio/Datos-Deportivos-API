package es.lanyu.participante.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.lanyu.participante.Participante;

@Repository
public interface ParticipanteDAO extends JpaRepository<Participante, String> {

}
