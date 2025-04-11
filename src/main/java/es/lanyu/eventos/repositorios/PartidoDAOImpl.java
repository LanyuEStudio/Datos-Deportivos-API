package es.lanyu.eventos.repositorios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.lanyu.commons.servicios.entidad.ServicioEntidad;
import es.lanyu.commons.string.StringUtils;
import es.lanyu.participante.Participante;

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
    ServicioEntidad servicioEntidad;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PartidoConId> getEventosConParticipanteConTexto(String txt) {
        String txtUpper = StringUtils.eliminarTildes(txt).toUpperCase();
        List<Participante> participantes = servicioEntidad.getElementosRegistradosDe(Participante.class).stream()
                    .filter(p -> StringUtils.eliminarTildes(p.getNombre()).toUpperCase().contains(txtUpper))
                    .collect(Collectors.toList());
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
