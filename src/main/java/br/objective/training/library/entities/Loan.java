package br.objective.training.library.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "LOAN")
public class Loan {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_OPERATOR")
    private Operator operator;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_USER")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_BOOK")
    private Book book;

    @Column(name = "WITHDRAWAL_DATE", nullable = false)
    private LocalDate withdrawalDate;

    @Column(name = "DATE_LIMIT_RETURN", nullable = false)
    private LocalDate dateLimitReturn;

    @Column(name = "DELIVERY_DATE", nullable = true)
    private LocalDate deliveryDate;

    public long getId() {
        return id;
    }

    public Loan setId(long id) {
        this.id = id;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public Loan setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Loan setUser(User user) {
        this.user = user;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Loan setBook(Book book) {
        this.book = book;
        return this;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public Loan setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
        return this;
    }

    public LocalDate getDateLimitReturn() {
        return dateLimitReturn;
    }

    public Loan setDateLimitReturn(LocalDate dateLimitReturn) {
        this.dateLimitReturn = dateLimitReturn;
        return this;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Loan setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id &&
                operator == loan.operator &&
                user == loan.user &&
                book == loan.book &&
                Objects.equals(withdrawalDate, loan.withdrawalDate) &&
                Objects.equals(dateLimitReturn, loan.dateLimitReturn) &&
                Objects.equals(deliveryDate, loan.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operator, user, book, withdrawalDate, dateLimitReturn, deliveryDate);
    }
}
