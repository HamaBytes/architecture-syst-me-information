package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservations")
public class ReservationController extends GenericCrudController<Reservation> {
    public ReservationController(IReservationService service) {
        super(service, service);
    }
}
