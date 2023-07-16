package br.objective.training.library.dto;

import br.objective.training.library.entities.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LoanDto {
    private long id;
    private long operatorId;
    private long userId;
    private long bookId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate withdrawalDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateLimitReturn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;

    public long getId() {
        return id;
    }

    public LoanDto setId(long id) {
        this.id = id;
        return this;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public LoanDto setOperatorId(long operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public LoanDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getBookId() {
        return bookId;
    }

    public LoanDto setBookId(long bookId) {
        this.bookId = bookId;
        return this;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public LoanDto setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
        return this;
    }

    public LocalDate getDateLimitReturn() {
        return dateLimitReturn;
    }

    public LoanDto setDateLimitReturn(LocalDate dateLimitReturn) {
        this.dateLimitReturn = dateLimitReturn;
        return this;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LoanDto setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public static LoanDto toDto(Loan loan) {
        LoanDto loanDto = new LoanDto();

        return loanDto.setId(loan.getId())
                .setOperatorId(loan.getOperator().getId())
                .setUserId(loan.getUser().getId())
                .setBookId(loan.getBook().getId())
                .setWithdrawalDate(loan.getWithdrawalDate())
                .setDateLimitReturn(loan.getDateLimitReturn())
                .setDeliveryDate(loan.getDeliveryDate());
    }

    public static List<LoanDto> toDto(List<Loan> loans){
        return loans.stream().map(LoanDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("LoanDto({id:%s, operatorId:%s, userId:%s, bookId:%s, withdrawalDate:%s, dateLimitReturn:%s, deliveryDate:%s})",
                this.getId(),
                this.getOperatorId(),
                this.getUserId(),
                this.getBookId(),
                this.getWithdrawalDate(),
                this.getDateLimitReturn(),
                this.getDeliveryDate());
    }
}
