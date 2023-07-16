package br.objective.training.library.service.impl;

import br.objective.training.library.dto.UserDto;
import br.objective.training.library.entities.User;
import br.objective.training.library.dto.request.UserRequestDto;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.repository.UserRepository;
import br.objective.training.library.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto create(UserRequestDto userRequestDto) {
        return UserDto.toDto(this.repository.save(this.toEntity(userRequestDto)));
    }

    @Override
    public UserDto update(long id, UserRequestDto userRequestDto) {
        User user = this.findByIdOrThrowNotFound(id);
        user.setName(userRequestDto.getName());

        return UserDto.toDto(this.repository.save(user));
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.toDto(this.repository.findAll());
    }

    @Override
    public UserDto findById(long id) {
        User user = this.findByIdOrThrowNotFound(id);

        return UserDto.toDto(user);
    }

    @Override
    public UserDto delete(long id) {
        User user = this.findByIdOrThrowNotFound(id);

        this.repository.delete(user);
        return UserDto.toDto(user);
    }

    private User findByIdOrThrowNotFound(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado."));
    }

    private User toEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());

        return user;
    }
}
