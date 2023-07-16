package br.objective.training.library.service.impl;

import br.objective.training.library.entities.Book;
import br.objective.training.library.dto.BookDto;
import br.objective.training.library.dto.request.BookRequestDto;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    private BookServiceImpl service;
    @Mock
    private BookRepository repository;
    private BookRequestDto requestDto;

    @BeforeEach
    public void setup() {
        this.service = new BookServiceImpl(repository);

        this.requestDto = this.createReqDto();
    }

    @Test
    @DisplayName("create: Esperado que ao receber um ISBN existente, retorne uma exceção")
    public void givenExistsIsbnWhenCreateThenException() {
        when(repository.findByIsbn(eq(this.requestDto.getIsbn()))).thenReturn(Optional.of(mock(Book.class)));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.create(this.requestDto));
        assertThat(badRequestException).hasMessage("Isbn já existe.");
        verify(repository, times(1)).findByIsbn(eq(this.requestDto.getIsbn()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber um ISBN inexistente e uma categoria inválida, retorne uma exceção")
    public void givenNotExistsIsbnAndNotExistCategoryWhenCreateThenException() {
        this.requestDto.setCategory("invalid");
        when(repository.findByIsbn(eq(this.requestDto.getIsbn()))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.create(this.requestDto));
        assertThat(badRequestException).hasMessage("Categoria não existe.");
        verify(repository, times(1)).findByIsbn(eq(this.requestDto.getIsbn()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("create: Esperado que ao receber um ISBN inexistente e uma categoria válida, retorne um livro")
    public void givenNotExistsIsbnAndExistCategoryWhenCreateThenCreateBook() {
        when(repository.findByIsbn(eq(this.requestDto.getIsbn()))).thenReturn(Optional.empty());
        when(repository.save(argThat(this::checkArgs
        ))).thenAnswer(onCreate -> {
            Book book = onCreate.getArgument(0, Book.class);
            book.setId(1L);
            return book;
        });
        BookDto bookDto = this.service.create(this.requestDto);
        verify(repository, times(1)).findByIsbn(eq(this.requestDto.getIsbn()));
        verify(repository, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(repository);
        assertThat(bookDto).hasToString("BookDto({id:1, title:Livro Treinamento, isbn:ASHS-FFRGGD89, edition:1, categories:[fantasia], authors:[John Doe], businessDayLoan:5, dayFineValueLoan:6.2, quantityCollection:3, mountBorrowed: 0, category:special})");
    }

    @Test
    @DisplayName("update: Esperado que ao receber o id de um livro inexistente, retorne uma exceção")
    public void givenNotExistsBookWhenUpdateThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.requestDto));
        assertThat(badRequestException).hasMessage("Livro não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber o id de um livro existente e um ISBN existente, retorne uma exceção")
    public void givenExistsBookAndExistIsbnWhenUpdateThenException() {
        Long id = 1L;
        when(repository.findById(eq(id))).thenReturn(Optional.of(mock(Book.class)));
        when(repository.findByIsbn(eq(this.requestDto.getIsbn()))).thenReturn(Optional.of(mock(Book.class)));
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.requestDto));
        assertThat(badRequestException).hasMessage("Isbn já existe.");
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).findByIsbn(eq(this.requestDto.getIsbn()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber o id de um livro existente, um ISBN inexistente e uma categoria inválida, retorne uma exceção")
    public void givenExistsBookAndNotExistIsbnAndNotExistCategoryWhenUpdateThenException() {
        Long id = 1L;
        this.requestDto.setCategory("invalid");
        when(repository.findById(eq(id))).thenReturn(Optional.of(mock(Book.class)));
        when(repository.findByIsbn(eq(this.requestDto.getIsbn()))).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.requestDto));
        assertThat(badRequestException).hasMessage("Categoria não existe.");
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).findByIsbn(eq(this.requestDto.getIsbn()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber o id de um livro existente, um ISBN inexistente e uma categoria válida, retorne o livro")
    public void givenExistsBookAndNotExistIsbnAndExistCategoryWhenUpdateThenUpdateBook() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        when(repository.findById(eq(id))).thenReturn(Optional.of(book));
        when(repository.findByIsbn(eq(this.requestDto.getIsbn()))).thenReturn(Optional.empty());
        BookDto bookDto = this.service.update(id, this.requestDto);
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).findByIsbn(eq(this.requestDto.getIsbn()));
        verifyNoMoreInteractions(repository);
        assertThat(bookDto).hasToString("BookDto({id:1, title:Livro Treinamento, isbn:ASHS-FFRGGD89, edition:1, categories:[fantasia], authors:[John Doe], businessDayLoan:5, dayFineValueLoan:6.2, quantityCollection:3, mountBorrowed: 0, category:special})");
    }

    @Test
    @DisplayName("findAll: Esperado que ao consultar todos os livros, retorne os livros")
    public void givenExistsBooksWhenFindAllThenBooks() {
        when(repository.findAll()).thenReturn(List.of(this.createBook(1L), this.createBook(2L)));
        List<BookDto> books = this.service.findAll();
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        assertThat(books.get(0)).hasToString("BookDto({id:1, title:Livro Treinamento 1, isbn:ASHS-FFRGGD89-1, edition:1, categories:[fantasia], authors:[John Doe], businessDayLoan:5, dayFineValueLoan:6.2, quantityCollection:3, mountBorrowed: 0, category:special})");
        assertThat(books.get(1)).hasToString("BookDto({id:2, title:Livro Treinamento 2, isbn:ASHS-FFRGGD89-2, edition:1, categories:[fantasia], authors:[John Doe], businessDayLoan:5, dayFineValueLoan:6.2, quantityCollection:3, mountBorrowed: 0, category:special})");
    }

    @Test
    @DisplayName("findById: Esperado que ao receber o id de um livro inexistente, retorne uma exceção")
    public void givenNotExistBookWhenFindByIdThenEmptyException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.findById(id));
        assertThat(badRequestException).hasMessage("Livro não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findById: Esperado que ao receber o id de um livro existente, retorne o livro")
    public void givenExistBookWhenFindByIdThenBook() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(this.createBook(id)));
        BookDto book = this.service.findById(id);
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
        assertThat(book).hasToString("BookDto({id:1, title:Livro Treinamento 1, isbn:ASHS-FFRGGD89-1, edition:1, categories:[fantasia], authors:[John Doe], businessDayLoan:5, dayFineValueLoan:6.2, quantityCollection:3, mountBorrowed: 0, category:special})");
    }

    @Test
    @DisplayName("delete: Esperado que ao receber o id de um livro inexistente, retorne uma exceção")
    public void givenNotExistBookWhenDeleteThenException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.delete(id));
        assertThat(badRequestException).hasMessage("Livro não encontrado.");
        verify(repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("delete: Esperado que ao receber um id de um livro existente, retorne o livro")
    public void givenExistBookWhenDeleteThenBook() {
        Long id = 1L;
        Book createdBook = createBook(id);
        when(repository.findById(id)).thenReturn(Optional.of(createdBook));
        BookDto book = this.service.delete(id);
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).delete(eq(createdBook));
        verifyNoMoreInteractions(repository);
        assertThat(book).hasToString("BookDto({id:1, title:Livro Treinamento 1, isbn:ASHS-FFRGGD89-1, edition:1, categories:[fantasia], authors:[John Doe], businessDayLoan:5, dayFineValueLoan:6.2, quantityCollection:3, mountBorrowed: 0, category:special})");
    }

    private BookRequestDto createReqDto() {
        BookRequestDto reqDto = new BookRequestDto();
        reqDto.setTitle("Livro Treinamento");
        reqDto.setIsbn("ASHS-FFRGGD89");
        reqDto.setEdition(1);
        List<String> categories = new ArrayList<String>();
        categories.add(0, "fantasia");
        reqDto.setCategories(categories);
        List<String> authors = new ArrayList<String>();
        authors.add(0, "John Doe");
        reqDto.setAuthors(authors);
        reqDto.setBusinessDayLoan(5);
        reqDto.setDayFineValueLoan(6.2);
        reqDto.setQuantityCollection(3);
        reqDto.setCategory("special");

        return reqDto;
    }

    private Book createBook(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(String.format("Livro Treinamento %s", id));
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
        book.setCategory("special");

        return book;
    }

    private boolean checkArgs(Book book) {
        return book.getTitle().equals(this.requestDto.getTitle()) &&
                book.getIsbn().equals(this.requestDto.getIsbn()) &&
                book.getEdition() == this.requestDto.getEdition() &&
                book.getCategories().equals(this.requestDto.getCategories()) &&
                book.getAuthors().equals(this.requestDto.getAuthors()) &&
                book.getBusinessDayLoan() == this.requestDto.getBusinessDayLoan() &&
                book.getDayFineValueLoan() == this.requestDto.getDayFineValueLoan() &&
                book.getQuantityCollection() == this.requestDto.getQuantityCollection() &&
                book.getCategory().equals(this.requestDto.getCategory());
    }
}