package br.objective.training.library.repository;

import br.objective.training.library.entities.Loan;
import br.objective.training.library.models.LoanSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("""
              SELECT l
              FROM Loan l
              JOIN FETCH l.book b
              WHERE MONTH(l.withdrawalDate) = :month
              AND YEAR(l.withdrawalDate) = :year
              ORDER BY b.title
            """)
    List<Loan> findAllLoansNotDeliveredByPeriod(@Param("month") final int month, @Param("year") final int year);

    @Query("""
              SELECT NEW br.objective.training.library.models.LoanSummary(
                   SUM(CASE WHEN e.deliveryDate IS NOT NULL AND e.deliveryDate <= e.dateLimitReturn THEN 1 ELSE 0 END),
                   SUM(CASE WHEN e.deliveryDate IS NOT NULL AND e.deliveryDate > e.dateLimitReturn THEN 1 ELSE 0 END),
                   SUM(CASE WHEN e.deliveryDate IS NULL AND e.withdrawalDate <= CURRENT_DATE() THEN 1 ELSE 0 END),
                   SUM(CASE WHEN e.deliveryDate IS NULL AND e.withdrawalDate > CURRENT_DATE() THEN 1 ELSE 0 END)
              ) FROM Loan e
            """)
    LoanSummary loanSummary();
}
