package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.strategy.OffreStrategyFactory;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {
    private final OffreStrategyFactory offreStrategyFactory;

    public VehiculeServiceImpl(
            IVehiculeRepository repository,
            OffreStrategyFactory offreStrategyFactory) {
        super(repository);
        this.offreStrategyFactory = offreStrategyFactory;
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule vehicule) {
        return vehicule.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule target, Vehicule source) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
        target.setEquipments(source.getEquipments());
    }

    @Override
    public boolean check(Vehicule vehicule, boolean isNew) {
        return vehicule != null
                && Utils.isNotEmpty(vehicule.getImmatriculation())
                && Utils.isNotEmpty(vehicule.getMarque())
                && Utils.isNotEmpty(vehicule.getModele())
                && vehicule.getCategorie() != null
                && vehicule.getTarifJournalier() != null
                && vehicule.getStatut() != null;
    }

    @Override
    public double calculTarif(Vehicule vehicule, int nombreJours, VehiculeOffre offre) {
        if (vehicule == null || vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException("Le véhicule et son tarif journalier sont obligatoires.");
        }
        if (nombreJours <= 0) {
            throw new IllegalArgumentException("Le nombre de jours doit être positif.");
        }
        if (offre == null) {
            throw new IllegalArgumentException("L'offre est obligatoire.");
        }
        return offreStrategyFactory.getStrategy(offre).calculer(vehicule, nombreJours);
    }
}
