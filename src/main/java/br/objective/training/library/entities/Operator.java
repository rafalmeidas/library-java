package br.objective.training.library.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OPERATOR")
public class Operator {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false ,length = 255)
    private String name;

    @Column(name = "EMAIL", nullable = false ,length = 255)
    private String email;

    @Column(name = "PASSWORD", nullable = false ,length = 255)
    private String password;

    public Long getId() {
        return id;
    }

    public Operator setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Operator setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Operator setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Operator setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return id == operator.id &&
                Objects.equals(name, operator.name) &&
                Objects.equals(email, operator.email) &&
                Objects.equals(password, operator.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }
}
