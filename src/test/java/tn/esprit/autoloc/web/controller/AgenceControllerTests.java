package tn.esprit.autoloc.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgenceController.class)
class AgenceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAgenceService agenceService;

    @Test
    void listAgenciesReturnsJson() throws Exception {
        Agence agence = new Agence();
        agence.setIdAgence(1L);
        agence.setNom("Agence Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("1 rue de Tunis");
        agence.setTelephone("71000000");
        when(agenceService.findAll()).thenReturn(List.of(agence));

        mockMvc.perform(get("/agences"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAgence").value(1))
                .andExpect(jsonPath("$[0].nom").value("Agence Tunis"));
    }

    @Test
    void createAgencyReturnsCreatedStatus() throws Exception {
        Agence created = new Agence();
        created.setIdAgence(3L);
        created.setNom("Agence Sfax");
        created.setVille("Sfax");
        created.setAdresse("2 avenue de Sfax");
        created.setTelephone("72000000");
        when(agenceService.create(org.mockito.ArgumentMatchers.any(Agence.class))).thenReturn(created);

        mockMvc.perform(post("/agences")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nom": "Agence Sfax",
                                  "ville": "Sfax",
                                  "adresse": "2 avenue de Sfax",
                                  "telephone": "72000000"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAgence").value(3));
    }

    @Test
    void missingAgencyReturnsStructuredNotFoundResponse() throws Exception {
        when(agenceService.findById(99L)).thenThrow(new ResourceNotFoundException("Agence", 99L));

        mockMvc.perform(get("/agences/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Agence introuvable avec l'identifiant 99"))
                .andExpect(jsonPath("$.horodatage").exists());
    }

    @Test
    void createAgencyRejectsProvidedId() throws Exception {
        when(agenceService.create(org.mockito.ArgumentMatchers.any(Agence.class)))
                .thenThrow(new IllegalArgumentException("L'identifiant de l'agence doit être vide."));

        mockMvc.perform(post("/agences")
                        .contentType("application/json")
                        .content("""
                                {
                                  "idAgence": 8,
                                  "nom": "Agence Sfax",
                                  "ville": "Sfax",
                                  "adresse": "2 avenue de Sfax",
                                  "telephone": "72000000"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void updateAgencyReturnsUpdatedAgency() throws Exception {
        Agence updated = new Agence();
        updated.setIdAgence(3L);
        updated.setNom("Agence Sfax");
        updated.setVille("Sfax");
        updated.setAdresse("2 avenue de Sfax");
        updated.setTelephone("72000000");
        when(agenceService.update(org.mockito.ArgumentMatchers.eq(3L), org.mockito.ArgumentMatchers.any(Agence.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/agences/3")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nom": "Agence Sfax",
                                  "ville": "Sfax",
                                  "adresse": "2 avenue de Sfax",
                                  "telephone": "72000000"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAgence").value(3));
    }

    @Test
    void deleteAgencyReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/agences/3"))
                .andExpect(status().isNoContent());
    }
}
