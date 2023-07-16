package br.objective.training.library.controller;

import br.objective.training.library.dto.BookDto;
import br.objective.training.library.dto.request.BookRequestDto;
import br.objective.training.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/books", produces = "application/json;charset=UTF-8")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(service.create(bookRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable("id") long id, @Valid @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(service.update(id, bookRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
