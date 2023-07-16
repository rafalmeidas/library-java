package br.objective.training.library.service.impl;

import br.objective.training.library.entities.Book;
import br.objective.training.library.dto.request.BookRequestDto;
import br.objective.training.library.dto.BookDto;
import br.objective.training.library.exception.BadRequestException;
import br.objective.training.library.repository.BookRepository;
import br.objective.training.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookDto create(BookRequestDto bookRequestDto) {
        this.findByIsbnIfExistThrowBadRequestException(bookRequestDto.getIsbn());

        this.notExistCategoryThrowBadRequestException(bookRequestDto.getCategory());

        return BookDto.toDto(this.repository.save(this.toEntity(bookRequestDto)));
    }

    @Override
    public BookDto update(Long id, BookRequestDto bookRequestDto) {
        Book book = this.findByIdOrThrowNotFound(id);

        this.findByIsbnIfExistThrowBadRequestException(bookRequestDto.getIsbn());

        this.notExistCategoryThrowBadRequestException(bookRequestDto.getCategory());

        return BookDto.toDto(this.toEntity(bookRequestDto, book));
    }

    @Override
    public List<BookDto> findAll() {
        return BookDto.toDto(this.repository.findAll());
    }

    @Override
    public BookDto findById(Long id) {
        Book book = findByIdOrThrowNotFound(id);

        return BookDto.toDto(book);
    }

    @Override
    public BookDto delete(Long id) {
        Book book = findByIdOrThrowNotFound(id);

        this.repository.delete(book);
        return BookDto.toDto(book);
    }

    private Book findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Livro não encontrado."));
    }

    private void findByIsbnIfExistThrowBadRequestException(String isbn) {
        if (this.repository.findByIsbn(isbn).isPresent()) {
            throw new BadRequestException("Isbn já existe.");
        }
    }

    private void notExistCategoryThrowBadRequestException(String category) {
        if (!this.checkExistCategory(category)) {
            throw new BadRequestException("Categoria não existe.");
        }
    }

    private boolean checkExistCategory(String category) {
        if (category.equals("normal") || category.equals("special")) {
            return true;
        }
        return false;
    }

    private Book toEntity(BookRequestDto bookRequestDto) {
        return this.toEntity(bookRequestDto, new Book());
    }

    private Book toEntity(BookRequestDto bookRequestDto, Book book) {
        book.setTitle(bookRequestDto.getTitle())
                .setIsbn(bookRequestDto.getIsbn())
                .setEdition(bookRequestDto.getEdition())
                .setCategories(bookRequestDto.getCategories())
                .setAuthors(bookRequestDto.getAuthors())
                .setBusinessDayLoan(bookRequestDto.getBusinessDayLoan())
                .setDayFineValueLoan(bookRequestDto.getDayFineValueLoan())
                .setQuantityCollection(bookRequestDto.getQuantityCollection())
                .setCategory(bookRequestDto.getCategory());

        return book;
    }
}
