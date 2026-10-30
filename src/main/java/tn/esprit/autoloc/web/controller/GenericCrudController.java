package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.autoloc.service.IGenericReadService;
import tn.esprit.autoloc.service.IGenericWriteService;

import java.util.List;

public abstract class GenericCrudController<T> {
    private final IGenericReadService<T, Long> readService;
    private final IGenericWriteService<T, Long> writeService;

    protected GenericCrudController(
            IGenericReadService<T, Long> readService,
            IGenericWriteService<T, Long> writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    @GetMapping
    @Operation(summary = "List all resources")
    public List<T> findAll() {
        return readService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a resource by its identifier")
    public T findById(@PathVariable Long id) {
        return readService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a resource")
    public ResponseEntity<T> create(@RequestBody T entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(writeService.create(entity));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a resource")
    public T update(@PathVariable Long id, @RequestBody T entity) {
        return writeService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a resource")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        writeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
