package br.objective.training.library.service;

import br.objective.training.library.dto.UserDto;
import br.objective.training.library.dto.request.UserRequestDto;

import java.util.List;

public interface UserService {
    UserDto create(UserRequestDto userRequestDto);

    UserDto update(long id, UserRequestDto userRequestDto);

    List<UserDto> findAll();

    UserDto findById(long id);

    UserDto delete(long id);
}
