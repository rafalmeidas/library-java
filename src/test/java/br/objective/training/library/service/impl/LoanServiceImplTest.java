package br.objective.training.library.service.impl;

import br.objective.training.library.clock.Clock;
import br.objective.training.library.dto.LoanDto;
import br.objective.training.library.dto.LoanFineDto;
import br.objective.training.library.dto.request.LoanRequestDto;
import br.objective.training.library.entities.Book;
import br.objective.training.library.entities.Loan;
import br.objective.training.library.entities.Operator;
import br.objective.training.library.entities.User;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.models.LoanSummary;
import br.objective.training.library.repository.BookRepository;
import br.objective.training.library.repository.LoanRepository;
import br.objective.training.library.repository.OperatorRepository;
import br.objective.training.library.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {
    private LoanServiceImpl service;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private OperatorRepository operatorRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private Clock clock;
    private LoanRequestDto requestDto;

    @BeforeEach
    public void setup() {
        service = new LoanServiceImpl(bookRepository, operatorRepository, userRepository, loanRepository, clock);
        requestDto = createReqDto();
    }

    @Test
    @DisplayName("create: Esperado que ao receber o id de um livro inexistente, retorne uma exceção")
    public void givenNotExistBookWhenCreateThenException() {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.save(requestDto));
        assertThat(badRequestException).hasMessage("Livro não encontrado.");
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber o id de um livro existente e que não tenha unidades disponíveis, retorne uma exceção")
    public void givenExistBookAndHasNoUnitsWhenCreateThenException() {
        Book book = createBook(0);
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(book));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.save(requestDto));
        assertThat(badRequestException).hasMessage("O Livro Treinamento 1 não possuí unidades disponíveis.");
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber o id de um livro existente que tenha unidades disponíveis e receba o id de um operador inexistente, retorne uma exceção")
    public void givenExistBookAndHasUnitsAndNotExistOperatorWhenCreateThenException() {
        Book book = createBook();
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(book));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.empty()));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.save(requestDto));
        assertThat(badRequestException).hasMessage("Operador não encontrado.");
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(operatorRepository, times(1)).findById(eq(requestDto.getOperatorId()));
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(operatorRepository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber o id de um livro existente que tenha unidades disponíveis, receba o id de um operador existente e o id de um usuário inexistente, retorne uma exceção")
    public void givenExistBookAndHasUnitsAndExistOperatorAndNotExistUserWhenCreateThenException() {
        Book book = createBook();
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(book));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(mock(Operator.class))));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.empty()));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.save(requestDto));
        assertThat(badRequestException).hasMessage("Usuário não encontrado.");
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(operatorRepository, times(1)).findById(eq(requestDto.getOperatorId()));
        verify(userRepository, times(1)).findById(eq(requestDto.getUserId()));
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(operatorRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber o id de um livro de categoria normal, retorne o empréstimo")
    public void givenExistBookWithCategoryNormalAndHasUnitsAndExistOperatorAndNotExistUserWhenCreateThenCreateLoan() {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("normal")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(createOperator())));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(createUser())));
        when(bookRepository.save(createBookAmountBorrowed(2))).thenReturn(createBookAmountBorrowed(2));
        when(loanRepository.save(argThat(this::checkArgs))).thenReturn(createLoan());
        LoanDto loanDto = service.save(requestDto);

        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(bookRepository, times(1)).save(eq(createBookAmountBorrowed(2)));
        verify(operatorRepository, times(1)).findById(eq(requestDto.getOperatorId()));
        verify(userRepository, times(1)).findById(eq(requestDto.getUserId()));
        verify(loanRepository, times(1)).save(any(Loan.class));
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(operatorRepository);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(loanRepository);
        assertThat(loanDto).hasToString("LoanDto({id:0, operatorId:1, userId:1, bookId:1, withdrawalDate:2023-04-27, dateLimitReturn:2023-05-03, deliveryDate:null})");
    }

    @Test
    @DisplayName("create: Esperado que ao receber o id de um livro de categoria especial, retorne o empréstimo")
    public void givenExistBookWithCategorySpecialAndHasUnitsAndExistOperatorAndNotExistUserWhenCreateThenCreateLoan() {
        Book book = createBookAmountBorrowed(2).setCategory("special");

        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("special")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(createOperator())));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(createUser())));
        when(bookRepository.save(book)).thenReturn(book);
        when(loanRepository.save(argThat(this::checkArgs))).thenReturn(createLoan());
        LoanDto loanDto = service.save(requestDto);

        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(bookRepository, times(1)).save(eq(book));
        verify(operatorRepository, times(1)).findById(eq(requestDto.getOperatorId()));
        verify(userRepository, times(1)).findById(eq(requestDto.getUserId()));
        verify(loanRepository, times(1)).save(any(Loan.class));
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(operatorRepository);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(loanRepository);
        assertThat(loanDto).hasToString("LoanDto({id:0, operatorId:1, userId:1, bookId:1, withdrawalDate:2023-04-27, dateLimitReturn:2023-05-03, deliveryDate:null})");
    }

    @Test
    @DisplayName("findFineLoan: Esperado que ao receber o id de um emprétimo inexistente, retorne uma exceção")
    public void givenNotExistLoanWhenFindFineLoanThenException() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.findFineLoan(id));
        assertThat(badRequestException).hasMessage("Empréstimo não encontrado.");
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("findFineLoan: Esperado que ao receber o id de um emprétimo existente que tenha a data de entrega, retorne uma exceção")
    public void givenExistLoanWithDeliveryDateWhenFindFineLoanThenException() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoanWithDeliveryDate(LocalDate.of(2023, 5, 3))));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.findFineLoan(id));
        assertThat(badRequestException).hasMessage("Empréstimo já entregue.");
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("findFineLoan: Esperado que ao receber o id de um emprétimo existente e o livro ser inexistente, retorne uma exceção")
    public void givenExistLoanNotExistBookWhenFindFineLoanThenException() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoan()));
        when(bookRepository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.findFineLoan(id));
        assertThat(badRequestException).hasMessage("Livro não encontrado.");
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("findFineLoan: Esperado que ao receber o id de um emprétimo existente e a data limite não ser maior que a atual, retorne os valores zerado")
    public void givenExistLoanWithDateLimitReturnNotExpiredWhenFindFineLoanThenFindFineLoan() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoan()));
        when(bookRepository.findById(eq(id))).thenReturn(Optional.of(createBook()));
        when(clock.now()).thenReturn(LocalDate.of(2023, 5, 3));
        LoanFineDto loanFineDto = service.findFineLoan(id);
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
        verifyNoMoreInteractions(bookRepository);
        assertThat(loanFineDto).hasToString("LoanFineDto({daysDelay:0, fineValue:0.0})");
    }

    @Test
    @DisplayName("findFineLoan: Esperado que ao receber o id de um emprétimo existente e a data limite ser maior que a atual, retorne os valores")
    public void givenExistLoanWithDateLimitReturnExpiredWhenFindFineLoanThenFindFineLoan() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoan(LocalDate.of(2023, 5, 1))));
        when(bookRepository.findById(eq(id))).thenReturn(Optional.of(createBook()));
        when(clock.now()).thenReturn(LocalDate.of(2023, 5, 3));
        LoanFineDto loanFineDto = service.findFineLoan(id);
        verify(bookRepository, times(1)).findById(eq(requestDto.getBookId()));
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
        verifyNoMoreInteractions(bookRepository);
        assertThat(loanFineDto).hasToString("LoanFineDto({daysDelay:2, fineValue:12.4})");
    }

    @Test
    @DisplayName("delivery: Esperado que ao receber o id de um emprétimo inexistente, retorne uma exceção")
    public void givenNotExistLoanWhenDeliveryThenException() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.delivery(id, LocalDate.of(2023, 5, 3)));
        assertThat(badRequestException).hasMessage("Empréstimo não encontrado.");
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("delivery: Esperado que ao receber o id de um emprétimo existente com a data de entrega inferior a data de retirada, retorne uma exceção")
    public void givenExistLoanAndWithdrawalDateWhenDeliveryBiggerThenDeliveryDateThenException() {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoan()));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> service.delivery(id, LocalDate.of(2023, 4, 3)));
        assertThat(badRequestException).hasMessage("Data de entrega inferior a data de retirada.");
        verify(loanRepository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("delivery: Esperado que ao receber o id de um emprétimo existente, retorne o empréstimo")
    public void givenExistLoanWhenDeliveryThenLoan() {
        Long id = 1L;
        LocalDate deliveryDate = LocalDate.of(2023, 5, 4);

        Book book = createBook().setAmountBorrowed(1);
        Loan loan = createLoan();
        Loan loanReturn = createLoan().setBook(book).setDeliveryDate(deliveryDate);

        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(loan));
        when(bookRepository.save(book)).thenReturn(book);
        when(loanRepository.save(loan)).thenReturn(loanReturn);

        LoanDto loanDto = service.delivery(id, deliveryDate);
        verify(loanRepository, times(1)).findById(eq(id));
        verify(bookRepository, times(1)).save(book);
        verify(loanRepository, times(1)).save(loan);
        verifyNoMoreInteractions(loanRepository);
        verifyNoMoreInteractions(bookRepository);
        assertThat(loanDto).hasToString("LoanDto({id:0, operatorId:1, userId:1, bookId:1, withdrawalDate:2023-04-27, dateLimitReturn:2023-05-03, deliveryDate:2023-05-04})");
    }

    @Test
    @DisplayName("notDelivered: Esperado que ao receber um mês e um ano, retorne uma lista de empréstimo")
    public void givenExistLoansWhenNotDeliveryThenLoans() {
        int month = 4;
        int year = 2023;
        when(loanRepository.findAllLoansNotDeliveredByPeriod(month, year)).thenReturn(List.of(createLoan(1L), createLoan(2L)));
        List<LoanDto> loansDto = service.notDelivered(month, year);
        verify(loanRepository, times(1)).findAllLoansNotDeliveredByPeriod(eq(month), eq(year));
        verifyNoMoreInteractions(loanRepository);
        assertThat(loansDto).hasToString("[LoanDto({id:1, operatorId:1, userId:1, bookId:1, withdrawalDate:2023-04-27, dateLimitReturn:2023-05-03, deliveryDate:null}), LoanDto({id:2, operatorId:1, userId:1, bookId:1, withdrawalDate:2023-04-27, dateLimitReturn:2023-05-03, deliveryDate:null})]");
    }

    @Test
    @DisplayName("summary: Esperado que ao receber o chamado, retorne o resumo")
    public void givenExistLoansWhenSummaryThenLoans() {
        when(loanRepository.loanSummary()).thenReturn(createLoanSummary());
        LoanSummary summary = service.summary();
        verify(loanRepository, times(1)).loanSummary();
        verifyNoMoreInteractions(loanRepository);
        assertThat(summary).hasToString("LoanSummary({totalDeliveredWithoutDelay:1, totalDeliveredWithDelay:10, totalOpenNoDelay:2, totalOpenWithDelay:3})");
    }

    private Book createBook() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle(String.format("Treinamento %s", id));
        book.setIsbn(String.format("ASHS-FFRGGD89-%s", id));
        book.setEdition(1);
        List<String> categories = new ArrayList<String>();
        categories.add(0, "fantasia");
        book.setCategories(categories);
        List<String> authors = new ArrayList<String>();
        authors.add(0, "John Doe");
        book.setAuthors(authors);
        book.setBusinessDayLoan(5);
        book.setDayFineValueLoan(6.2);
        book.setQuantityCollection(3);
        book.setAmountBorrowed(2);
        book.setCategory("normal");

        return book;
    }

    private Book createBook(int amount) {
        Book book = createBook();
        book.setQuantityCollection(amount);
        return book;
    }

    private Book createBookAmountBorrowed(int amount) {
        Book book = createBook();
        book.setAmountBorrowed(amount);
        return book;
    }

    private Book createBook(String category) {
        Book book = createBook();
        book.setCategory(category);
        return book;
    }

    private Operator createOperator() {
        Long id = 1L;
        Operator operator = new Operator();
        operator.setId(id);
        operator.setName("John Dow");
        operator.setPassword("123");
        operator.setEmail(String.format("john_doe%s@fake.com", id));

        return operator;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Ana Doe");
        return user;
    }

    private Loan createLoan() {
        Loan loan = new Loan();

        loan.setOperator(createOperator());
        loan.setUser(createUser());
        loan.setBook(createBook());
        loan.setWithdrawalDate(LocalDate.of(2023, 4, 27));
        loan.setDateLimitReturn(LocalDate.of(2023, 5, 3));
        return loan;
    }

    private Loan createLoan(Long id) {
        Loan loan = createLoan();
        loan.setId(id);

        return loan;
    }

    private Loan createLoan(LocalDate dateLimitReturn) {
        Loan loan = createLoan();
        loan.setDateLimitReturn(dateLimitReturn);
        return loan;
    }

    private Loan createLoanWithDeliveryDate(LocalDate deliveryDate) {
        Loan loan = createLoan(1L);
        loan.setDeliveryDate(deliveryDate);
        return loan;
    }

    private boolean checkArgs(Loan loan) {
        return
                loan.getOperator().getId() == requestDto.getOperatorId() &&
                        loan.getUser().getId() == requestDto.getUserId() &&
                        loan.getBook().getId() == requestDto.getBookId() &&
                        loan.getWithdrawalDate().equals(requestDto.getWithdrawalDate()) &&
                        loan.getDateLimitReturn().equals(requestDto.getDateLimitReturn());
    }

    private LoanSummary createLoanSummary() {
        LoanSummary summary = new LoanSummary(1,10,2,3);
        return summary;
    }

    private LoanRequestDto createReqDto() {
        LoanRequestDto reqDto = new LoanRequestDto();
        reqDto.setBookId(1L);
        reqDto.setOperatorId(1L);
        reqDto.setUserId(1L);
        reqDto.setWithdrawalDate(LocalDate.of(2023, 5, 3));

        return reqDto;
    }
}