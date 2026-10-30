package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

@RestController
@RequestMapping("/maintenances")
@Tag(name = "Maintenances")
public class MaintenanceController extends GenericCrudController<Maintenance> {
    public MaintenanceController(IMaintenanceService service) {
        super(service, service);
    }
}
