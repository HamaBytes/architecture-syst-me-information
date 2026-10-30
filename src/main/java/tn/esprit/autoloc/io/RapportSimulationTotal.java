package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;

import java.util.Locale;

@Component
public class RapportSimulationTotal implements RapportSimulation {
    @Override
    public String generer(Agence agence, int nombreJours, VehiculeOffre offre, double total) {
        return "Total des tarifs (" + nombreJours + " jours, " + offre + ") : "
                + String.format(Locale.ROOT, "%.2f", total);
    }
}
