package tn.esprit.autoloc.io;

import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RapportSimulationTests {
    @Test
    void decoratorPrintsAgencyHeaderAndReturnsTotalWithOffer() {
        Agence agence = new Agence();
        agence.setIdAgence(4L);
        agence.setNom("Agence Ariana");
        agence.setTelephone("71000000");

        ByteArrayOutputStream console = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        String report;
        try {
            System.setOut(new PrintStream(console, true, StandardCharsets.UTF_8));
            RapportSimulation rapport = new RapportSimulationEnteteDecorator(new RapportSimulationTotal());
            report = rapport.generer(agence, 2, VehiculeOffre.WEEKEND, 160.0);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("Total des tarifs (2 jours, WEEKEND) : 160.00", report);
        assertTrue(console.toString(StandardCharsets.UTF_8).contains("Agence 4 | Agence Ariana | 71000000"));
    }
}
