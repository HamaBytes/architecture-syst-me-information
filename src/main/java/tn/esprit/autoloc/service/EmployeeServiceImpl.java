package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Employee;
import tn.esprit.autoloc.repository.IEmployeeRepository;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, Long> implements IEmployeeService {
    public EmployeeServiceImpl(IEmployeeRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Employee";
    }

    @Override
    protected Long getId(Employee employee) {
        return employee.getIdEmployee();
    }

    @Override
    protected void setFields(Employee target, Employee source) {
        target.setNom(source.getNom());
        target.setPrenom(source.getPrenom());
        target.setRole(source.getRole());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Employee employee, boolean isNew) {
        return employee != null
                && Utils.isNotEmpty(employee.getNom())
                && Utils.isNotEmpty(employee.getPrenom())
                && employee.getRole() != null;
    }
}
