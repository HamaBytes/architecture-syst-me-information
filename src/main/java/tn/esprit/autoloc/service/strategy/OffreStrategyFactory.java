package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.calcul.VehiculeOffre;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OffreStrategyFactory {
    private final Map<VehiculeOffre, OffreStrategy> strategies;

    public OffreStrategyFactory(List<OffreStrategy> offreStrategies) {
        strategies = new EnumMap<>(VehiculeOffre.class);
        for (OffreStrategy strategy : offreStrategies) {
            OffreStrategy previous = strategies.put(strategy.offre(), strategy);
            if (previous != null) {
                throw new IllegalStateException("Plusieurs stratégies sont définies pour l'offre "
                        + strategy.offre() + ".");
            }
        }
    }

    public OffreStrategy getStrategy(VehiculeOffre offre) {
        OffreStrategy strategy = strategies.get(offre);
        if (strategy == null) {
            throw new IllegalArgumentException("Aucune stratégie n'est définie pour l'offre " + offre + ".");
        }
        return strategy;
    }
}
