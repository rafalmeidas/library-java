package br.objective.training.library.service.impl;

import br.objective.training.library.clock.Clock;
import br.objective.training.library.dto.LoanDto;
import br.objective.training.library.entities.Book;
import br.objective.training.library.entities.Loan;
import br.objective.training.library.dto.request.LoanRequestDto;
import br.objective.training.library.dto.LoanFineDto;
import br.objective.training.library.entities.Operator;
import br.objective.training.library.entities.User;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.models.LoanSummary;
import br.objective.training.library.repository.BookRepository;
import br.objective.training.library.repository.LoanRepository;
import br.objective.training.library.repository.OperatorRepository;
import br.objective.training.library.repository.UserRepository;
import br.objective.training.library.service.LoanService;
import br.objective.training.library.utils.DateUtils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private OperatorRepository operatorRepository;
    private UserRepository userRepository;
    private Clock clock;

    public LoanServiceImpl(BookRepository bookRepository,
                           OperatorRepository operatorRepository,
                           UserRepository userRepository,
                           LoanRepository loanRepository,
                           Clock clock) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.operatorRepository = operatorRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }

    @Override
    public LoanDto save(LoanRequestDto loanRequestDto) {
        Book book = findBookByIdOrThrowNotFound(loanRequestDto.getBookId());

        if (book.getQuantityCollection() <= book.getAmountBorrowed()) {
            throw new BadRequestException(String.format("O Livro %s não possuí unidades disponíveis.", book.getTitle()));
        }

        Operator operator = findOperatorByIdOrThrowNotFound(loanRequestDto.getOperatorId());
        User user = findUserByIdOrThrowNotFound(loanRequestDto.getUserId());

        if (book.getCategory().equals("normal")) {
            loanRequestDto.setDateLimitReturn(DateUtils.addDaysSkipWeekends(loanRequestDto.getWithdrawalDate(), book.getBusinessDayLoan()));
        } else {
            loanRequestDto.setDateLimitReturn(DateUtils.addDays(loanRequestDto.getWithdrawalDate(), book.getBusinessDayLoan()));
        }

        incrementAmountBorrowed(book);

        Loan loan = toEntity(loanRequestDto, operator, user, book);
        return LoanDto.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanFineDto findFineLoan(Long id) {
        Loan loan = findLoanByIdOrThrowNotFound(id);

        if (loan.getDeliveryDate() != null) {
            throw new BadRequestException("Empréstimo já entregue.");
        }

        Book book = findBookByIdOrThrowNotFound(loan.getBook().getId());

        long daysDelay = ChronoUnit.DAYS.between(this.clock.now(), loan.getDateLimitReturn());

        if (daysDelay <= 0) {
            long absDaysDelay = Math.abs(daysDelay);
            double fineValue = book.getDayFineValueLoan() * absDaysDelay;
            return new LoanFineDto(absDaysDelay, fineValue);
        }

        return new LoanFineDto(0, 0);
    }

    @Override
    public LoanDto delivery(Long id, LocalDate deliveryDate) {
        Loan loan = findLoanByIdOrThrowNotFound(id);

        if (loan.getDeliveryDate() != null) {
            throw new BadRequestException("Empréstimo já entregue.");
        }

        if (ChronoUnit.DAYS.between(loan.getWithdrawalDate(), deliveryDate) < 0) {
            throw new BadRequestException("Data de entrega inferior a data de retirada.");
        }

        decrementAmountBorrowed(loan.getBook());
        loan.setDeliveryDate(deliveryDate);

        return LoanDto.toDto(loanRepository.save(loan));
    }

    @Override
    public List<LoanDto> notDelivered(int month, int year) {
        return LoanDto.toDto(loanRepository.findAllLoansNotDeliveredByPeriod(month, year));
    }

    @Override
    public LoanSummary summary() {
        return loanRepository.loanSummary();
    }

    private void incrementAmountBorrowed(Book book){
        book.setAmountBorrowed(book.getAmountBorrowed() + 1);
        bookRepository.save(book);
    }

    private void decrementAmountBorrowed(Book book){
        book.setAmountBorrowed(book.getAmountBorrowed() - 1);
        bookRepository.save(book);
    }

    private Loan findLoanByIdOrThrowNotFound(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Empréstimo não encontrado."));
    }

    private Book findBookByIdOrThrowNotFound(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Livro não encontrado."));
    }

    private Operator findOperatorByIdOrThrowNotFound(Long id) {
        return operatorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Operador não encontrado."));
    }

    private User findUserByIdOrThrowNotFound(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado."));
    }

    private Loan toEntity(LoanRequestDto loanRequestDto, Operator operator, User user, Book book) {
        Loan loan = new Loan();

        return loan.setOperator(operator)
                .setUser(user)
                .setBook(book)
                .setWithdrawalDate(loanRequestDto.getWithdrawalDate())
                .setDateLimitReturn(loanRequestDto.getDateLimitReturn());
    }
}
