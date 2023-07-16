package br.objective.training.library.controller;

import br.objective.training.library.dto.OperatorDto;
import br.objective.training.library.dto.request.OperatorRequestDto;
import br.objective.training.library.service.OperatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/operators", produces = "application/json;charset=UTF-8")
public class OperatorController {
    private final OperatorService service;

    public OperatorController(OperatorService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<OperatorDto> create(@Valid @RequestBody OperatorRequestDto operatorRequestDto) {
        return ResponseEntity.ok(service.create(operatorRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<OperatorDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OperatorDto> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
