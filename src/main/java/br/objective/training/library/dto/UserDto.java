package br.objective.training.library.dto;

import br.objective.training.library.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto implements Serializable {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public UserDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        return userDto.setId(user.getId())
                .setName((user.getName()));
    }

    public static List<UserDto> toDto(List<User> users) {
        return users.stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("UserDto({id:%s, name:%s})", getId(), getName());
    }
}
