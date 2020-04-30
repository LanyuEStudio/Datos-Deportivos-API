package es.lanyu.eventos.rest;

import java.util.List;

import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.PartidoDAO;

// Si se quiere inyectar PersistentEntityResourceAssembler hace falta
// marcar el controlador con @RepositoryRestController 
// https://stackoverflow.com/a/51487736
// Siguiendo la guia de la documentacion:
// https://docs.spring.io/spring-data/rest/docs/current/reference/html/#customizing-sdr.overriding-sdr-response-handlers
@RepositoryRestController
// Se puede marcar todo el tipo como respuesta web
// @ResponseBody
public class PartidoController {

    private PartidoDAO partidoDAO;

    PartidoController(PartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }

    @GetMapping("/partidos/search/con-nombre-participante")
    // Para devolver una coleccion hay que usar CollectionModel<PersistentEntityResource>
    // Diferencia entre un recurso individual y una coleccion
    // https://docs.spring.io/spring-hateoas/docs/current/reference/html/#fundamentals.resources
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getPartidosConParticipanteComo(@RequestParam String txt,
            // Inyectar PersistentEntityResourceAssembler para devolver como HATEOAS en el metodo
            // https://stackoverflow.com/a/31782016
            PersistentEntityResourceAssembler assembler) {
        List<PartidoConId> partidos = partidoDAO.getEventosConParticipanteConTexto(txt);

        return assembler.toCollectionModel(partidos);
    }

}
