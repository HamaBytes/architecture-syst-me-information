package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Equipment;

public interface IEquipmentRepository extends JpaRepository<Equipment, Long> {

}
