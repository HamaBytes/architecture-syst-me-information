package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService {
    private final IAgenceRepository agenceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IVehiculeService vehiculeService;

    public AgenceServiceImpl(
            IAgenceRepository agenceRepository,
            IVehiculeRepository vehiculeRepository,
            IVehiculeService vehiculeService) {
        super(agenceRepository);
        this.agenceRepository = agenceRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.vehiculeService = vehiculeService;
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence agence) {
        return agence.getIdAgence();
    }

    @Override
    protected void setFields(Agence target, Agence source) {
        target.setNom(source.getNom());
        target.setVille(source.getVille());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
    }

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return agence != null
                && Utils.isNotEmpty(agence.getNom())
                && Utils.isNotEmpty(agence.getVille())
                && Utils.isNotEmpty(agence.getAdresse())
                && Utils.isNotEmpty(agence.getTelephone());
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        List<Vehicule> vehicules = vehiculeRepository.findByAgence_IdAgence(id);
        vehicules.forEach(vehicule -> vehicule.setAgence(null));
        vehiculeRepository.saveAll(vehicules);
        agenceRepository.deleteById(id);
    }

    @Override
    public boolean hasVehicules(Long id) {
        findById(id);
        return vehiculeRepository.existsByAgence_IdAgence(id);
    }

    @Override
    public String simuler(Long id, int nombreJours, VehiculeOffre offre) {
        if (nombreJours <= 0) {
            throw new IllegalArgumentException("Le nombre de jours doit être positif.");
        }
        if (offre == null) {
            throw new IllegalArgumentException("L'offre est obligatoire.");
        }

        Agence agence = findById(id);
        List<Vehicule> vehicules = vehiculeRepository.findByAgence_IdAgence(id);
        double total = vehicules.stream()
                .mapToDouble(vehicule -> vehiculeService.calculTarif(vehicule, nombreJours, offre))
                .sum();

        return "Agence " + agence.getIdAgence() + " | " + agence.getNom()
                + " | " + agence.getTelephone()
                + System.lineSeparator() + "Total des tarifs (" + nombreJours + " jours, " + offre + ") : "
                + String.format(java.util.Locale.ROOT, "%.2f", total);
    }
}
