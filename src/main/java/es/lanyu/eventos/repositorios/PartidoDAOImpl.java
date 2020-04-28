package es.lanyu.eventos.repositorios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.lanyu.participante.Participante;
import es.lanyu.participante.repositorios.ParticipanteDAO;

// Ver: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions
@Transactional(readOnly = true)
// Siguiendo la documentacion:
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations
// Se pueden implementar parcialmente metodos personalizados que incluso pueden ser reutilizados ya
// que usamos EventoDAOCustom y puede servir para otros tipos de eventos aparte de Partido
// Pero esto no expone los metodos, simplemente proporciona la implementacion de los metodos
// que no puede hacer automaticamente JPA. Es importante usar el sufijo "Impl" (o configurar otro)
class PartidoDAOImpl implements EventoDAOCustom<PartidoConId> {

    @Autowired
    ParticipanteDAO participanteDAO;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PartidoConId> getEventosConParticipanteConTexto(String txt) {
        // Reutilizo los metodos que ya tengo disponibles
        List<Participante> participantes = participanteDAO.findByNombreIgnoreCaseContaining(txt);
        // Para que no se repitan partidos
        Set<PartidoConId> partidos = new HashSet<PartidoConId>();
        Query query = entityManager.createNativeQuery(
                "SELECT p.* FROM partidos as p " +
                "WHERE p.local = ?1 OR p.visitante = ?1", PartidoConId.class);
        participantes.forEach(p -> {
            // Solo cambia el parametro para cada participante
            query.setParameter(1, p.getIdentificador());
            partidos.addAll(query.getResultList());
        });

        return new ArrayList<PartidoConId>(partidos);
    }

}
