package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipment;

public interface IEquipmentService extends
        IGenericReadService<Equipment, Long>,
        IGenericWriteService<Equipment, Long>,
        IGenericValidationService<Equipment> {
}
