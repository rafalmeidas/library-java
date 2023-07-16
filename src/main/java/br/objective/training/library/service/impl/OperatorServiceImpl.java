package br.objective.training.library.service.impl;

import br.objective.training.library.dto.OperatorDto;
import br.objective.training.library.entities.Operator;
import br.objective.training.library.dto.request.OperatorRequestDto;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.repository.OperatorRepository;
import br.objective.training.library.service.OperatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {
    private OperatorRepository repository;

    public OperatorServiceImpl(OperatorRepository repository) {
        this.repository = repository;
    }

    @Override
    public OperatorDto create(OperatorRequestDto operatorRequestDto) {
        if (this.repository.findByEmail(operatorRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException("E-mail já existe.");
        }

        return OperatorDto.toDto(this.repository.save(toEntity(operatorRequestDto)));
    }

    @Override
    public List<OperatorDto> findAll() {
        return OperatorDto.toDto(this.repository.findAll());
    }

    @Override
    public OperatorDto findById(Long id) {
        Operator operator = this.findByIdOrThrowNotFound(id);

        return OperatorDto.toDto(operator);
    }

    @Override
    public OperatorDto delete(Long id) {
        Operator operator = findByIdOrThrowNotFound(id);

        this.repository.delete(operator);

        return OperatorDto.toDto(operator);
    }

    private Operator findByIdOrThrowNotFound(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Operador não encontrado."));
    }

    private Operator toEntity(OperatorRequestDto operatorRequestDto) {
        Operator operator = new Operator();
        operator.setName(operatorRequestDto.getName())
                .setEmail(operatorRequestDto.getEmail())
                .setPassword(operatorRequestDto.getPassword());

        return operator;
    }
}
