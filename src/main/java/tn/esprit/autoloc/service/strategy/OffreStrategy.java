package tn.esprit.autoloc.service.strategy;

import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;

public interface OffreStrategy {
    VehiculeOffre offre();

    double calculer(Vehicule vehicule, int nombreJours);
}
