package tn.esprit.autoloc.io;

import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;

public interface RapportSimulation {
    String generer(Agence agence, int nombreJours, VehiculeOffre offre, double total);
}
