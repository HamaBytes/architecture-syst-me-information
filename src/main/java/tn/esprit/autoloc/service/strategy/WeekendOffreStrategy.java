package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;

@Component
public class WeekendOffreStrategy implements OffreStrategy {
    @Override
    public VehiculeOffre offre() {
        return VehiculeOffre.WEEKEND;
    }

    @Override
    public double calculer(Vehicule vehicule, int nombreJours) {
        return vehicule.getTarifJournalier().doubleValue() * nombreJours * 0.80;
    }
}
