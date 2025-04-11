package es.lanyu.eventos.rest;

import java.time.Instant;
import java.util.List;

import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.lanyu.eventos.repositorios.SucesoConId;
import es.lanyu.eventos.repositorios.SucesoDAO;

@RepositoryRestController
public class SucesoController {

    private SucesoDAO sucesoDAO;

    SucesoController(SucesoDAO sucesoDAO) {
        this.sucesoDAO = sucesoDAO;
    }

    @GetMapping("/sucesos/search/participante/{id}/entre-fechas")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getSucesosConIdParticipanteEntreFechas(
            // Ahora el descubrimiento indica el nombre del RequestParam si existe en vez del nombre del parametro
            @PathVariable("id") String id, @RequestParam("inicio") Instant comienzo, @RequestParam Instant fin,
            PersistentEntityResourceAssembler assembler) {
        List<SucesoConId> sucesos = sucesoDAO.findByIdParticipanteAndTemporalBetween(id, comienzo, fin);

        return assembler.toCollectionModel(sucesos);
    }

}
