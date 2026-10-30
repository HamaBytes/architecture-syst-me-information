package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;

@Component
public class LongueDureeOffreStrategy implements OffreStrategy {
    @Override
    public VehiculeOffre offre() {
        return VehiculeOffre.LONGUE_DUREE;
    }

    @Override
    public double calculer(Vehicule vehicule, int nombreJours) {
        double total = vehicule.getTarifJournalier().doubleValue() * nombreJours * 0.75;
        return total > 2000 ? total * 0.95 : total;
    }
}
