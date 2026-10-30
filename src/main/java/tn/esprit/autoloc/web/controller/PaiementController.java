package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@Tag(name = "Paiements", description = "Consultation en lecture seule des paiements")
public class PaiementController {
    private final IPaiementService service;

    public PaiementController(IPaiementService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lister les paiements")
    public List<Paiement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un paiement")
    public Paiement findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
