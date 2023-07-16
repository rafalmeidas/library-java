package br.objective.training.library.service.impl;

import br.objective.training.library.dto.UserDto;
import br.objective.training.library.dto.request.UserRequestDto;
import br.objective.training.library.entities.User;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    private UserRequestDto requestDto;

    @BeforeEach
    public void setup() {
        this.service = new UserServiceImpl(repository);

        this.requestDto = this.createReqDto();
    }

    @Test
    @DisplayName("create: Esperado que ao receber um usuário, retorne o usuário")
    public void givenExistEmailWhenCreateThenException() {
        when(repository.save(argThat(this::checkArgs))).thenReturn(createEntity());
        UserDto userDto = this.service.create(requestDto);
        verify(repository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(repository);
        assertThat(userDto).hasToString("UserDto({id:1, name:John Doe})");
    }

    @Test
    @DisplayName("update: Esperado que ao receber o id de um usuário inexistente, retorne uma exceção")
    public void givenNotExistUserWhenUpdateThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, requestDto));
        assertThat(badRequestException).hasMessage("Usuário não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber o id de um usuário existente, retorne o usuário")
    public void givenExistUserWhenUpdateThenException() {
        Long id = 1L;
        User user = createEntity();
        when(repository.findById(eq(id))).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        UserDto userDto = this.service.update(id, requestDto);
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
        assertThat(userDto).hasToString("UserDto({id:1, name:John Doe})");
    }

    @Test
    @DisplayName("findAll: Esperado que ao consultar todos usuários, retorne os usuários")
    public void givenExistsUsersWhenFindAllThenUsers() {
        when(repository.findAll()).thenReturn(List.of(createEntity(1L), createEntity(2L)));
        List<UserDto> operators = this.service.findAll();
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        assertThat(operators.get(0)).hasToString("UserDto({id:1, name:John Doe})");
        assertThat(operators.get(1)).hasToString("UserDto({id:2, name:John Doe})");
    }

    @Test
    @DisplayName("findById: Esperado que ao receber o id de um usuário inexistente, retorne uma exceção")
    public void givenNotExistUserWhenFindByIdThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.findById(id));
        assertThat(badRequestException).hasMessage("Usuário não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findById: Esperado que ao receber o id de um usuário existente, retorne o usuário")
    public void givenExistUserWhenFindByIdThenUser() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.of(createEntity()));
        UserDto userDto = this.service.findById(id);
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
        assertThat(userDto).hasToString("UserDto({id:1, name:John Doe})");
    }

    @Test
    @DisplayName("delete: Esperado que ao receber o id de um usuário inexistente, retorne uma exceção")
    public void givenNotExistUserWhenDeleteThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.delete(id));
        assertThat(badRequestException).hasMessage("Usuário não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("delete: Esperado que ao receber o id de um usuário existente, retorne o usuário")
    public void givenExistUserWhenDeleteThenUser() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.of(createEntity()));
        UserDto userDto = this.service.delete(id);
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).delete(createEntity());
        verifyNoMoreInteractions(repository);
        assertThat(userDto).hasToString("UserDto({id:1, name:John Doe})");
    }

    private UserRequestDto createReqDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("John Doe");
        return userRequestDto;
    }

    private User createEntity() {
        User user = new User();
        user.setId(1L);
        user.setName(requestDto.getName());
        return user;
    }

    private User createEntity(Long id) {
        User user = createEntity();
        user.setId(id);
        return user;
    }

    private boolean checkArgs(User user) {
        return user.getName().equals(requestDto.getName());
    }
}