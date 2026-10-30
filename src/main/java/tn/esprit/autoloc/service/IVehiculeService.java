package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeService extends
        IGenericReadService<Vehicule, Long>,
        IGenericWriteService<Vehicule, Long>,
        IGenericValidationService<Vehicule> {
}
