package tn.esprit.autoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class VehiculeServiceTests {
    private VehiculeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new VehiculeServiceImpl(mock(IVehiculeRepository.class));
    }

    @Test
    void calculTarifUsesBaseDailyPrice() {
        assertEquals(300.0, service.calculTarif(vehicule(100), 3, VehiculeOffre.STANDARD));
    }

    @Test
    void calculTarifAppliesWeekendDiscount() {
        assertEquals(240.0, service.calculTarif(vehicule(100), 3, VehiculeOffre.WEEKEND));
    }

    @Test
    void calculTarifAppliesLongTermDiscountAndThresholdDiscount() {
        assertEquals(2137.5, service.calculTarif(vehicule(1000), 3, VehiculeOffre.LONGUE_DUREE));
    }

    @Test
    void calculTarifDoesNotApplyThresholdDiscountBelowTwoThousand() {
        assertEquals(1500.0, service.calculTarif(vehicule(1000), 2, VehiculeOffre.LONGUE_DUREE));
    }

    @Test
    void calculTarifRejectsInvalidDayCount() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculTarif(vehicule(100), 0, VehiculeOffre.STANDARD));
    }

    private static Vehicule vehicule(double tarifJournalier) {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(BigDecimal.valueOf(tarifJournalier));
        return vehicule;
    }
}
