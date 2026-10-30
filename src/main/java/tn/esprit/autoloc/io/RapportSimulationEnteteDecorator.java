package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;

@Component
public class RapportSimulationEnteteDecorator implements RapportSimulation {
    private final RapportSimulationTotal rapport;

    public RapportSimulationEnteteDecorator(RapportSimulationTotal rapport) {
        this.rapport = rapport;
    }

    @Override
    public String generer(Agence agence, int nombreJours, VehiculeOffre offre, double total) {
        System.out.println("Agence " + agence.getIdAgence() + " | " + agence.getNom()
                + " | " + agence.getTelephone());
        return rapport.generer(agence, nombreJours, offre, total);
    }
}
