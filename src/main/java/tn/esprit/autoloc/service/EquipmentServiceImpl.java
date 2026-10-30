package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Equipment;
import tn.esprit.autoloc.repository.IEquipmentRepository;

@Service
public class EquipmentServiceImpl extends GenericServiceImpl<Equipment, Long> implements IEquipmentService {
    public EquipmentServiceImpl(IEquipmentRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Equipment";
    }

    @Override
    protected Long getId(Equipment equipment) {
        return equipment.getIdEquipment();
    }

    @Override
    protected void setFields(Equipment target, Equipment source) {
        target.setLibelle(source.getLibelle());
    }

    @Override
    public boolean check(Equipment equipment, boolean isNew) {
        return equipment != null && Utils.isNotEmpty(equipment.getLibelle());
    }
}
