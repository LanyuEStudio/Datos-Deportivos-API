package es.lanyu.eventos.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.PartidoDAO;

// Si se quiere inyectar PersistentEntityResourceAssembler hace falta
// marcar el controlador con @RepositoryRestController
@RepositoryRestController
public class PartidoController {
    
	private PartidoDAO partidoDAO;
	
	PartidoController(PartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }
	
	@GetMapping("/partidos/search/con-nombre-participante")
	@ResponseBody
	public CollectionModel<PersistentEntityResource> getPartidosConParticipanteComo(@RequestParam String txt,
			PersistentEntityResourceAssembler assembler) {
		List<PartidoConId> partidos = partidoDAO.getEventosConParticipanteConTexto(txt);

		return assembler.toCollectionModel(partidos);
	}
	
  	@GetMapping("/partidos/search/participante/{id}")
  	@ResponseBody
  	public CollectionModel<PersistentEntityResource> getPartidosParticipanteLocalVisitante(@PathVariable String id,
  	        @RequestParam Optional<Boolean> local,
  	        PersistentEntityResourceAssembler assembler) {
  	    List<PartidoConId> partidos = partidoDAO.findByIdLocalOrIdVisitante(id, id).stream()
  	        .filter(p -> {
  	            boolean filtro = false;
                try {
                    filtro = local.orElse(true) ? p.getIdLocal().equals(id) : p.getIdVisitante().equals(id);
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
                return filtro;
            })
  	        .collect(Collectors.toList());

        return assembler.toCollectionModel(partidos);
    }
	
}
