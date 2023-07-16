package br.objective.training.library.service;

import br.objective.training.library.dto.OperatorDto;
import br.objective.training.library.dto.request.OperatorRequestDto;

import java.util.List;

public interface OperatorService {
    OperatorDto create(OperatorRequestDto operatorRequestDto);

    List<OperatorDto> findAll();

    OperatorDto findById(Long id);

    OperatorDto delete(Long id);
}
