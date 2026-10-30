package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService extends IGenericService<Agence, Long>, IAgenceSimulation {
    boolean hasVehicules(Long id);
}
