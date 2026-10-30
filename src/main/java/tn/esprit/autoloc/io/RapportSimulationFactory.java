package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;

@Component
public class RapportSimulationFactory {
    private final RapportSimulation rapport;

    public RapportSimulationFactory(RapportSimulationEnteteDecorator rapport) {
        this.rapport = rapport;
    }

    public RapportSimulation creer() {
        return rapport;
    }
}
