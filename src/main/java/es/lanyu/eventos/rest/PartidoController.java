package es.lanyu.eventos.rest;

import java.util.List;

import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.lanyu.eventos.repositorios.PartidoConId;
import es.lanyu.eventos.repositorios.PartidoDAO;

// Si se quiere inyectar PersistentEntityResourceAssembler hace falta
// marcar el controlador con @RepositoryRestController
@RepositoryRestController
@RequestMapping(path="/partidos/search")
public class PartidoController {
    
	private PartidoDAO partidoDAO;
	
	PartidoController(PartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }
	
	@GetMapping("/con-nombre-participante")
	@ResponseBody
	public CollectionModel<PersistentEntityResource> getPartidosConParticipanteComo(@RequestParam String txt,
			PersistentEntityResourceAssembler assembler) {
		List<PartidoConId> partidos = partidoDAO.getEventosConParticipanteConTexto(txt);

		return assembler.toCollectionModel(partidos);
	}
	
}
