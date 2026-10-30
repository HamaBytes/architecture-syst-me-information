package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

public interface IClientService extends
        IGenericReadService<Client, Long>,
        IGenericWriteService<Client, Long>,
        IGenericValidationService<Client> {
}
