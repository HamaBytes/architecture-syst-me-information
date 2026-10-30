package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class BasicAgenceServiceTests {
    @Test
    void simulerThrowsUntilTheSimulationIsImplemented() {
        BasicAgenceServiceImpl service = new BasicAgenceServiceImpl(mock(IAgenceRepository.class));

        assertThrows(UnsupportedOperationException.class,
                () -> service.simuler(new Agence(), 1, VehiculeOffre.STANDARD));
    }
}
