package br.objective.training.library.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserRequestDto implements Serializable {
    @NotBlank
    @Size(min = 4, max=250)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
