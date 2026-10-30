package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@Tag(name = "Agences", description = "Gestion des agences de location")
public class AgenceController {
    private final IAgenceService agenceService;

    public AgenceController(IAgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @GetMapping
    @Operation(summary = "Lister les agences", description = "Retourne toutes les agences.")
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une agence", description = "Retourne une agence à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    public Agence findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Ajouter une agence", description = "Crée une agence sans identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée"),
            @ApiResponse(responseCode = "400", description = "Agence invalide")
    })
    public ResponseEntity<Agence> create(@RequestBody Agence agence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(agence));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une agence", description = "Met à jour les informations de l'agence.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiée"),
            @ApiResponse(responseCode = "400", description = "Agence invalide"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une agence", description = "Supprime l'agence après désaffectation de ses véhicules.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/simuler")
    @Operation(summary = "Simuler le coût des véhicules d'une agence")
    public String simuler(
            @PathVariable Long id,
            @Parameter(description = "Nombre de jours de location") @RequestParam int nombreJours,
            @Parameter(description = "Type d'offre") @RequestParam VehiculeOffre offre) {
        Agence agence = agenceService.findById(id);
        return agenceService.simuler(agence, nombreJours, offre);
    }

}
