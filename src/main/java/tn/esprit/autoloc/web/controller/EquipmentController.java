package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Equipment;
import tn.esprit.autoloc.service.IEquipmentService;

@RestController
@RequestMapping("/equipments")
@Tag(name = "Equipments")
public class EquipmentController extends GenericCrudController<Equipment> {
    public EquipmentController(IEquipmentService service) {
        super(service);
    }
}
