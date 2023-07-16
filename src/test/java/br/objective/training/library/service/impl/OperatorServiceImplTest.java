package br.objective.training.library.service.impl;

import br.objective.training.library.dto.OperatorDto;
import br.objective.training.library.dto.request.OperatorRequestDto;
import br.objective.training.library.entities.Operator;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.repository.OperatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperatorServiceImplTest {
    private OperatorServiceImpl service;
    @Mock
    private OperatorRepository repository;
    private OperatorRequestDto requestDto;

    @BeforeEach
    public void setup() {
        this.service = new OperatorServiceImpl(repository);

        this.requestDto = this.createReqDto();
    }

    @Test
    @DisplayName("create: Esperado que ao receber um e-mail existente, retorne uma exceção")
    public void givenExistEmailWhenCreateThenException() {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.of(createEntity()));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.create(this.requestDto));
        assertThat(badRequestException).hasMessage("E-mail já existe.");
        verify(repository, times(1)).findByEmail(eq(this.requestDto.getEmail()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber um e-mail inexistente, retorne o operador")
    public void givenNotExistEmailWhenCreateThenException() {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        when(repository.save(argThat(this::checkArgs))).thenReturn(createEntity());
        this.service.create(this.requestDto);
        verify(repository, times(1)).findByEmail(eq(this.requestDto.getEmail()));
        verify(repository, times(1)).save(any(Operator.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findAll: Esperado que ao consultar todos operadores, retorne os operadores")
    public void givenExistsOperatorsWhenFindAllThenOperators() {
        when(repository.findAll()).thenReturn(List.of(createEntity(1L), createEntity(2L)));
        List<OperatorDto> operators = this.service.findAll();
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        assertThat(operators.get(0)).hasToString("OperatorDto({id:1, name:John Doe, email:john_doe1@fake.com, password:123})");
        assertThat(operators.get(1)).hasToString("OperatorDto({id:2, name:John Doe, email:john_doe2@fake.com, password:123})");
    }

    @Test
    @DisplayName("findById: Esperado que ao receber o id de um operador inexistente, retorne uma exceção")
    public void givenNotExistOperatorWhenFindByIdThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.findById(id));
        assertThat(badRequestException).hasMessage("Operador não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findById: Esperado que ao receber o id de um operador existente, retorne o operador")
    public void givenExistOperatorWhenFindByIdThenOperator() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.of(createEntity()));
        OperatorDto operatorDto = this.service.findById(id);
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
        assertThat(operatorDto).hasToString("OperatorDto({id:1, name:John Doe, email:john_doe@fake.com, password:123})");
    }

    @Test
    @DisplayName("delete: Esperado que ao receber o id de um operador inexistente, retorne uma exceção")
    public void givenNotExistOperatorWhenDeleteThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.delete(id));
        assertThat(badRequestException).hasMessage("Operador não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("delete: Esperado que ao receber o id de um operador existente, retorne o operador")
    public void givenExistOperatorWhenDeleteThenOperator() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.of(createEntity()));
//        when(repository.delete(argThat(this::checkArgs))).thenReturn(createEntity());
        OperatorDto operatorDto = this.service.delete(id);
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).delete(createEntity());
        verifyNoMoreInteractions(repository);
        assertThat(operatorDto).hasToString("OperatorDto({id:1, name:John Doe, email:john_doe@fake.com, password:123})");
    }

    private OperatorRequestDto createReqDto() {
        OperatorRequestDto reqDto = new OperatorRequestDto();
        reqDto.setName("John Doe");
        reqDto.setEmail("john_doe@fake.com");
        reqDto.setPassword("123");

        return reqDto;
    }

    private Operator createEntity() {
        Operator operator = new Operator();
        operator.setId(1L);
        operator.setName(requestDto.getName());
        operator.setEmail(requestDto.getEmail());
        operator.setPassword(requestDto.getPassword());

        return operator;
    }

    private Operator createEntity(Long id) {
        Operator operator = createEntity();
        operator.setId(id);
        operator.setEmail(String.format("john_doe%s@fake.com", id));

        return operator;
    }

    private boolean checkArgs(Operator operator) {
        return operator.getName().equals(requestDto.getName()) &&
                operator.getEmail().equals(requestDto.getEmail()) &&
                operator.getPassword().equals(requestDto.getPassword());
    }
}