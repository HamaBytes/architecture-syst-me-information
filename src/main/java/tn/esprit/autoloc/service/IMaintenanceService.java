package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;

public interface IMaintenanceService extends
        IGenericReadService<Maintenance, Long>,
        IGenericWriteService<Maintenance, Long>,
        IGenericValidationService<Maintenance> {
}
