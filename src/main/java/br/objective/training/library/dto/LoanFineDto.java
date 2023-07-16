package br.objective.training.library.dto;

import java.io.Serializable;

public class LoanFineDto implements Serializable {
    private long daysDelay;
    private double fineValue;

    public LoanFineDto(long daysDelay, double fineValue) {
        this.daysDelay = daysDelay;
        this.fineValue = fineValue;
    }

    public long getDaysDelay() {
        return daysDelay;
    }

    public double getFineValue() {
        return fineValue;
    }

    @Override
    public String toString() {
        return String.format("LoanFineDto({daysDelay:%s, fineValue:%s})",
                this.getDaysDelay(),
                this.getFineValue());
    }
}
