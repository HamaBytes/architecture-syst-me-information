package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@RestController
@RequestMapping("/vehicules")
@Tag(name = "Vehicules")
public class VehiculeController extends GenericCrudController<Vehicule> {
    public VehiculeController(IVehiculeService service) {
        super(service);
    }
}
