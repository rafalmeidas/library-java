package br.objective.training.library.service;

import br.objective.training.library.dto.LoanDto;
import br.objective.training.library.dto.request.LoanRequestDto;
import br.objective.training.library.dto.LoanFineDto;
import br.objective.training.library.models.LoanSummary;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {
    LoanDto save(LoanRequestDto loanRequestDto);

    LoanFineDto findFineLoan(Long id);

    LoanDto delivery(Long id, LocalDate deliveryDate);

    List<LoanDto> notDelivered(int month, int year);

    LoanSummary summary();
}
