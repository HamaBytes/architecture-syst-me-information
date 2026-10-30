package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employee;

public interface IEmployeeService extends
        IGenericReadService<Employee, Long>,
        IGenericWriteService<Employee, Long>,
        IGenericValidationService<Employee> {
}
