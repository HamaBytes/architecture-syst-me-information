package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService extends
        IGenericReadService<Agence, Long>,
        IGenericWriteService<Agence, Long>,
        IGenericValidationService<Agence> {
    boolean hasVehicules(Long id);
}
