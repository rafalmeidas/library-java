package br.objective.training.library.controller;

import br.objective.training.library.clock.ClockImp;
import br.objective.training.library.dto.request.LoanRequestDto;
import br.objective.training.library.entities.Book;
import br.objective.training.library.entities.Loan;
import br.objective.training.library.entities.Operator;
import br.objective.training.library.entities.User;
import br.objective.training.library.models.LoanSummary;
import br.objective.training.library.repository.BookRepository;
import br.objective.training.library.repository.LoanRepository;
import br.objective.training.library.repository.OperatorRepository;
import br.objective.training.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

class LoanControllerTest extends AbstractControllerTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private OperatorRepository operatorRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private LoanRepository loanRepository;
    @MockBean
    private ClockImp clock;
    private LoanRequestDto requestDto;

    @BeforeEach
    public void setup() {
        requestDto = createReqDto();
    }

    @Test
    @DisplayName("PUT /api/loans/withdrawal: Esperado que ao receber um dto inválido, com id do operador menor que 1, retorne uma exceção")
    public void givenLoansWhenCreateWithOperatorIdLessThan1ThenExpects400() throws Exception {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("normal")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(mock(Operator.class))));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(mock(User.class))));
        requestDto.setOperatorId(0);
        mockMvc.perform(put("/api/loans/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "operatorId",
                                                "message": "deve ser maior que ou igual à 1"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/withdrawal: Esperado que ao receber um dto inválido, com id do usuário menor que 1, retorne uma exceção")
    public void givenLoansWhenCreateWithUserIdLessThan1ThenExpects400() throws Exception {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("normal")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(mock(Operator.class))));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(mock(User.class))));
        requestDto.setUserId(0);
        mockMvc.perform(put("/api/loans/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "userId",
                                                "message": "deve ser maior que ou igual à 1"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/withdrawal: Esperado que ao receber um dto inválido, com id do livro menor que 1, retorne uma exceção")
    public void givenLoansWhenCreateWithBookIdLessThan1ThenExpects400() throws Exception {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("normal")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(mock(Operator.class))));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(mock(User.class))));
        requestDto.setBookId(0);
        mockMvc.perform(put("/api/loans/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "bookId",
                                                "message": "deve ser maior que ou igual à 1"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/withdrawal: Esperado que ao receber um dto inválido, com data de retirada nula, retorne uma exceção")
    public void givenLoansWhenCreateWithWithdrawDateNullThenExpects400() throws Exception {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("normal")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(mock(Operator.class))));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(mock(User.class))));
        requestDto.setWithdrawalDate(null);
        mockMvc.perform(put("/api/loans/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "withdrawalDate",
                                                "message": "não deve ser nulo"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/withdrawal: Esperado que ao receber um dto válido, retorne o LoanDto")
    public void givenLoansWhenCreateThenExpects200() throws Exception {
        when(bookRepository.findById(eq(requestDto.getBookId()))).thenReturn(Optional.of(createBook("normal")));
        when(operatorRepository.findById(eq(requestDto.getOperatorId()))).thenReturn((Optional.of(createOperator())));
        when(userRepository.findById(eq(requestDto.getUserId()))).thenReturn((Optional.of(createUser())));
        when(loanRepository.save(argThat(this::checkArgs))).thenReturn(createLoan());
        mockMvc.perform(put("/api/loans/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);

                    JSONAssert.assertEquals("""
                            {
                                "id":1,
                                "operatorId":1,
                                "userId":1,
                                "bookId":1,
                                "withdrawalDate":"27/04/2023",
                                "dateLimitReturn":"03/05/2023",
                                "deliveryDate":null
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/$id/withdrawal: Esperado que ao receber o id de um empréstimo existente, retorne o LoanFineDto")
    public void givenLoansWhenFindFineLoanWithLimitDateNotLaterTheCurrentDateThenExpects200() throws Exception {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoan()));
        when(bookRepository.findById(eq(id))).thenReturn(Optional.of(createBook()));
        when(clock.now()).thenReturn(LocalDate.of(2023, 5, 3));
        mockMvc.perform(get("/api/loans/1/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);

                    JSONAssert.assertEquals("""
                            {
                                "daysDelay":0,
                                "fineValue":0.0
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/$id/withdrawal: Esperado que ao receber o id de um empréstimo existente, retorne o LoanFineDto")
    public void givenLoansWhenFindFineLoanWithLimitDateLaterTheCurrentDateThenExpects200() throws Exception {
        Long id = 1L;
        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(createLoan(LocalDate.of(2023, 5, 1))));
        when(bookRepository.findById(eq(id))).thenReturn(Optional.of(createBook()));
        when(clock.now()).thenReturn(LocalDate.of(2023, 5, 3));
        mockMvc.perform(get("/api/loans/1/withdrawal")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            {
                                "daysDelay":2,
                                "fineValue":12.4
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/$id/delivery: Esperado que ao receber uma data de devolução válida, retorne o LoanDto")
    public void givenLoansWhenDeliveryLoanThenExpects200() throws Exception {
        Long id = 1L;
        Book book = createBook().setAmountBorrowed(1);
        Loan loan = createLoan();
        Loan loanReturn = createLoan().setBook(book).setDeliveryDate(LocalDate.of(2023, 5, 4));

        when(loanRepository.findById(eq(id))).thenReturn(Optional.of(loan));
        when(bookRepository.save(book)).thenReturn(book);
        when(loanRepository.save(loan)).thenReturn(loanReturn);
        mockMvc.perform(put("/api/loans/1/delivery")
                        .param("delivery-date", "04/05/2023")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto))
                )
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            {
                                "id":1,
                                "operatorId":1,
                                "userId":1,
                                "bookId":1,
                                "withdrawalDate":"27/04/2023",
                                "dateLimitReturn":"03/05/2023",
                                "deliveryDate":"04/05/2023"
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/not-delivered: Esperado que ao receber um mês e um ano, retorne uma lista de LoanDto")
    public void givenLoansWhenNotDeliveryThenExpects200() throws Exception {
        when(loanRepository.findAllLoansNotDeliveredByPeriod(1, 2023)).thenReturn(List.of(createLoan(1L), createLoan(2L)));
        mockMvc.perform(get("/api/loans/not-delivered")
                        .param("month", "1")
                        .param("year", "2023")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            [
                                {
                                    "id":1,
                                    "operatorId":1,
                                    "userId":1,
                                    "bookId":1,
                                    "withdrawalDate":"27/04/2023",
                                    "dateLimitReturn":"03/05/2023",
                                    "deliveryDate":null
                                },
                                {
                                    "id":2,
                                    "operatorId":1,
                                    "userId":1,
                                    "bookId":1,
                                    "withdrawalDate":"27/04/2023",
                                    "dateLimitReturn":"03/05/2023",
                                    "deliveryDate":null
                                }
                            ]
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("PUT /api/loans/summary: Esperado que ao receber um mês e um ano, retorne uma lista de LoanDto")
    public void givenLoansWhenSummaryThenExpects200() throws Exception {
        when(loanRepository.loanSummary()).thenReturn(createLoanSummary());
        mockMvc.perform(get("/api/loans/summary")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            {
                                "totalDeliveredWithoutDelay":1,
                                "totalDeliveredWithDelay":10,
                                "totalOpenNoDelay":2,
                                "totalOpenWithDelay":3
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
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
        Long id = 1L;

        loan.setId(id);
        loan.setOperator(createOperator());
        loan.setUser(createUser());
        loan.setBook(createBook());
        loan.setWithdrawalDate(LocalDate.of(2023, 4, 27));
        loan.setDateLimitReturn(LocalDate.of(2023, 5, 3));
        return loan;
    }

    private Loan createLoan(LocalDate dateLimitReturn) {
        Loan loan = createLoan();
        loan.setDateLimitReturn(dateLimitReturn);
        return loan;
    }

    private Loan createLoan(Long id) {
        Loan loan = createLoan();
        loan.setId(id);
        return loan;
    }

    private boolean checkArgs(Loan loan) {
        return
                loan.getOperator().getId() == requestDto.getOperatorId() &&
                        loan.getUser().getId() == requestDto.getUserId() &&
                        loan.getBook().getId() == requestDto.getBookId() &&
                        loan.getWithdrawalDate().equals(requestDto.getWithdrawalDate());
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