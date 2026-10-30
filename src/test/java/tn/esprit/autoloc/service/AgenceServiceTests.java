package tn.esprit.autoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AgenceServiceTests {
    private IAgenceRepository agenceRepository;
    private IVehiculeRepository vehiculeRepository;
    private IVehiculeService vehiculeService;
    private AgenceServiceImpl service;

    @BeforeEach
    void setUp() {
        agenceRepository = mock(IAgenceRepository.class);
        vehiculeRepository = mock(IVehiculeRepository.class);
        vehiculeService = mock(IVehiculeService.class);
        service = new AgenceServiceImpl(agenceRepository, vehiculeRepository, vehiculeService);
    }

    @Test
    void createAgencePersistsValidAgencyWithoutId() {
        Agence agence = validAgence();
        when(agenceRepository.save(agence)).thenReturn(agence);

        assertSame(agence, service.create(agence));
        verify(agenceRepository).save(agence);
    }

    @Test
    void createAgenceRejectsAnExistingId() {
        Agence agence = validAgence();
        agence.setIdAgence(10L);

        assertThrows(IllegalArgumentException.class, () -> service.create(agence));
        verify(agenceRepository, never()).save(any(Agence.class));
    }

    @Test
    void createAgenceRejectsMissingRequiredFields() {
        Agence agence = validAgence();
        agence.setNom(" ");

        assertThrows(IllegalArgumentException.class, () -> service.create(agence));
        verify(agenceRepository, never()).save(any(Agence.class));
    }

    @Test
    void findByIdThrowsWhenAgencyDoesNotExist() {
        when(agenceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void updateCopiesAgencyFieldsOntoExistingRecord() {
        Agence current = validAgence();
        current.setIdAgence(4L);
        Agence changes = validAgence();
        changes.setNom("Agence mise à jour");
        when(agenceRepository.findById(4L)).thenReturn(Optional.of(current));
        when(agenceRepository.save(current)).thenReturn(current);

        Agence result = service.update(4L, changes);

        assertSame(current, result);
        assertEquals("Agence mise à jour", result.getNom());
    }

    @Test
    void deleteAgencyDetachesItsVehiclesBeforeDeletingIt() {
        Vehicule vehicule = new Vehicule();
        vehicule.setAgence(validAgence());
        when(agenceRepository.existsById(4L)).thenReturn(true);
        when(vehiculeRepository.findByAgence_IdAgence(4L)).thenReturn(List.of(vehicule));

        service.deleteById(4L);

        assertNull(vehicule.getAgence());
        verify(vehiculeRepository).saveAll(List.of(vehicule));
        verify(agenceRepository).deleteById(4L);
    }

    @Test
    void simulerReturnsAgencyHeaderAndTotalVehiclePrice() {
        Agence agence = validAgence();
        agence.setIdAgence(4L);
        Vehicule first = new Vehicule();
        Vehicule second = new Vehicule();
        when(agenceRepository.findById(4L)).thenReturn(Optional.of(agence));
        when(vehiculeRepository.findByAgence_IdAgence(4L)).thenReturn(List.of(first, second));
        when(vehiculeService.calculTarif(first, 3, VehiculeOffre.STANDARD)).thenReturn(300.0);
        when(vehiculeService.calculTarif(second, 3, VehiculeOffre.STANDARD)).thenReturn(240.0);

        String report = service.simuler(4L, 3, VehiculeOffre.STANDARD);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertTrue(report.contains("4 | Agence Tunis | 71000000")),
                () -> assertTrue(report.contains("3 jours, STANDARD")),
                () -> assertTrue(report.contains("540.00"))
        );
    }

    @Test
    void simulerRejectsNonPositiveDuration() {
        assertThrows(IllegalArgumentException.class, () -> service.simuler(4L, 0, VehiculeOffre.STANDARD));
    }

    private static Agence validAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("1 rue de Tunis");
        agence.setTelephone("71000000");
        return agence;
    }
}
