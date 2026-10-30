package tn.esprit.autoloc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.io.RapportSimulationFactory;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@Primary
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService {
    private final IAgenceRepository agenceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IVehiculeService vehiculeService;
    private final RapportSimulationFactory rapportFactory;

    public AgenceServiceImpl(
            IAgenceRepository agenceRepository,
            IVehiculeRepository vehiculeRepository,
            IVehiculeService vehiculeService,
            RapportSimulationFactory rapportFactory) {
        super(agenceRepository);
        this.agenceRepository = agenceRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.vehiculeService = vehiculeService;
        this.rapportFactory = rapportFactory;
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
    public String simuler(Agence agence, int nombreJours, VehiculeOffre offre) {
        if (agence == null) {
            throw new IllegalArgumentException("L'agence est obligatoire.");
        }
        if (nombreJours <= 0) {
            throw new IllegalArgumentException("Le nombre de jours doit être positif.");
        }
        if (offre == null) {
            throw new IllegalArgumentException("L'offre est obligatoire.");
        }

        double total = agence.getVehicules().stream()
                .mapToDouble(vehicule -> vehiculeService.calculTarif(vehicule, nombreJours, offre))
                .sum();

        return rapportFactory.creer()
                .generer(agence, nombreJours, offre, total);
    }
}