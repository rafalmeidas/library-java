package br.objective.training.library.controller;

import br.objective.training.library.dto.UserDto;
import br.objective.training.library.dto.request.UserRequestDto;
import br.objective.training.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json;charset=UTF-8")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(service.create(userRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(service.update(id, userRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
