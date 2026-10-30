package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;

public interface IContratService extends
        IGenericReadService<Contrat, Long>,
        IGenericWriteService<Contrat, Long>,
        IGenericValidationService<Contrat> {
}
