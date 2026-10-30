package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

@RestController
@RequestMapping("/contrats")
@Tag(name = "Contrats")
public class ContratController extends GenericCrudController<Contrat> {
    public ContratController(IContratService service) {
        super(service);
    }
}
