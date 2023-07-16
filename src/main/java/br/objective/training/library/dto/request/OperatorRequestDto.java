package br.objective.training.library.dto.request;

import javax.validation.constraints.*;
import java.io.Serializable;

public class OperatorRequestDto implements Serializable {
    @NotBlank
    @Size(min = 4, max=250)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max=250)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
