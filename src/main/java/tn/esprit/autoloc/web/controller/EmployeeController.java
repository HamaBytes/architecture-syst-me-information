package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Employee;
import tn.esprit.autoloc.service.IEmployeeService;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees")
public class EmployeeController extends GenericCrudController<Employee> {
    public EmployeeController(IEmployeeService service) {
        super(service);
    }
}
