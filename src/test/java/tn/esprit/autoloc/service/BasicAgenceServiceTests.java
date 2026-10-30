package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicAgenceServiceTests {
    @Test
    void simulerDelegatesToSimulationImplementation() {
        IAgenceSimulation agenceSimulation = mock(IAgenceSimulation.class);
        BasicAgenceServiceImpl service = new BasicAgenceServiceImpl(
                mock(IAgenceRepository.class), agenceSimulation);
        Agence agence = new Agence();
        String report = "Total des tarifs (1 jour, STANDARD) : 0.00";
        when(agenceSimulation.simuler(agence, 1, VehiculeOffre.STANDARD)).thenReturn(report);

        assertEquals(report, service.simuler(agence, 1, VehiculeOffre.STANDARD));
        verify(agenceSimulation).simuler(agence, 1, VehiculeOffre.STANDARD);
    }
}
