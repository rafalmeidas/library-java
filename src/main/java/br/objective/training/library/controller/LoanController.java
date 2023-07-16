package br.objective.training.library.controller;

import br.objective.training.library.dto.LoanDto;
import br.objective.training.library.dto.LoanFineDto;
import br.objective.training.library.dto.request.LoanRequestDto;
import br.objective.training.library.models.LoanSummary;
import br.objective.training.library.service.LoanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/loans", produces = "application/json;charset=UTF-8")
public class LoanController {
    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<LoanDto> save(@Valid @RequestBody LoanRequestDto loanRequestDto) {
        return ResponseEntity.ok(service.save(loanRequestDto));
    }

    @GetMapping("/{id}/withdrawal")
    public ResponseEntity<LoanFineDto> findFineLoan(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findFineLoan(id));
    }

    @PutMapping("/{id}/delivery")
    public ResponseEntity<LoanDto> delivery(@RequestParam(name = "delivery-date", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate deliveryDate, @PathVariable("id") long id) {
        return ResponseEntity.ok(service.delivery(id, deliveryDate));
    }

    @GetMapping("/not-delivered")
    public ResponseEntity<List<LoanDto>> notDelivered(@RequestParam(name = "month", required = true) int month, @RequestParam(name = "year", required = true) int year) {
        return ResponseEntity.ok(service.notDelivered(month, year));
    }

    @GetMapping("/summary")
    public ResponseEntity<LoanSummary> summary(){
        return ResponseEntity.ok(service.summary());
    }
}
