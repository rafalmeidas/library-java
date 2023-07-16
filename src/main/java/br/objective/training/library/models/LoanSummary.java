package br.objective.training.library.models;

public record LoanSummary(long totalDeliveredWithoutDelay, long totalDeliveredWithDelay, long totalOpenNoDelay, long totalOpenWithDelay) {
    @Override
    public String toString() {
        return String.format("LoanSummary({totalDeliveredWithoutDelay:%s, totalDeliveredWithDelay:%s, totalOpenNoDelay:%s, totalOpenWithDelay:%s})",
                totalDeliveredWithoutDelay,
                totalDeliveredWithDelay,
                totalOpenNoDelay,
                totalOpenWithDelay);
    }
}
