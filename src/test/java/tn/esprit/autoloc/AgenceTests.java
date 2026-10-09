package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AgenceTests {
    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "CrudRepository (basic)");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "JpaRepository (full)");
    }


    @Test
    void loadSortedAgences() {
        List<Agence> agences = fullAgenceRepository.findAll(
                Sort.by(Sort.Direction.DESC, "idAgence")
        );

        agences.forEach(agence -> System.out.println(
                agence.getIdAgence() + " | " + agence.getNom() + " | " + agence.getVille()));
    }

    @Test
    void loadPagedAgences() {
        Page<Agence> page = fullAgenceRepository.findAll(
                PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "idAgence"))
        );

        System.out.println("Total pages : " + page.getTotalPages());
        System.out.println("Current page : " + page.getNumber());
        page.getContent().forEach(agence -> System.out.println(
                agence.getIdAgence() + " | " + agence.getNom() + " | " + agence.getVille()));
    }

    private void addAgence(CrudRepository<Agence, Long> repository) {
        int suffix = (int) System.currentTimeMillis();

        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("TU96_" + suffix);
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("TU95_" + suffix);
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setCategorie(CategorieVehicule.UTILITAIRE);
        v2.setStatut(StatutVehicule.DISPONIBLE);
        v2.setTarifJournalier(new BigDecimal("80"));
        v2.setAgence(agence);

        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        repository.save(agence);
    }

    private void loadAgence(CrudRepository<Agence, Long> repository, String repositoryType) {
        Iterable<Agence> agences = repository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("\nRepository : ").append(repositoryType);
        for (Agence agence : agences) {
            sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom());
            sb.append("\nVehicules Count : ").append(agence.getVehicules().size());
            for (Vehicule vehicule : agence.getVehicules()) {
                sb.append("\n=== ").append(vehicule.getIdVehicule()).append("|").append(vehicule.getImmatriculation());
            }
        }
        fail(sb.toString());
    }

}


interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}
