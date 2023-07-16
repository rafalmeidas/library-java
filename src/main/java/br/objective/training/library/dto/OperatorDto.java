package br.objective.training.library.dto;

import br.objective.training.library.entities.Operator;

import java.util.List;
import java.util.stream.Collectors;

public class OperatorDto {
    private long id;
    private String name;
    private String email;
    private String password;

    public long getId() {
        return id;
    }

    public OperatorDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OperatorDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OperatorDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OperatorDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public static OperatorDto toDto(Operator operator) {
        OperatorDto operatorDto = new OperatorDto();

        return operatorDto.setId(operator.getId())
                .setName(operator.getName())
                .setEmail(operator.getEmail())
                .setPassword(operator.getPassword());
    }

    public static List<OperatorDto> toDto(List<Operator> operators) {
        return operators.stream().map(OperatorDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("OperatorDto({id:%s, name:%s, email:%s, password:%s})", getId(), getName(), getEmail(), getPassword());
    }
}
