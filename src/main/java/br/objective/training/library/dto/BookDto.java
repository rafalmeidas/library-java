package br.objective.training.library.dto;

import br.objective.training.library.entities.Book;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class BookDto implements Serializable {
    private long id;
    private String title;
    private String isbn;
    private int edition;
    private List<String> categories;
    private List<String> authors;
    private int businessDayLoan;
    private double dayFineValueLoan;
    private int quantityCollection;
    private int amountBorrowed;
    private String category;

    public long getId() {
        return id;
    }

    public BookDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public int getEdition() {
        return edition;
    }

    public BookDto setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public BookDto setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public BookDto setAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public int getBusinessDayLoan() {
        return businessDayLoan;
    }

    public BookDto setBusinessDayLoan(int businessDayLoan) {
        this.businessDayLoan = businessDayLoan;
        return this;
    }

    public double getDayFineValueLoan() {
        return dayFineValueLoan;
    }

    public BookDto setDayFineValueLoan(double dayFineValueLoan) {
        this.dayFineValueLoan = dayFineValueLoan;
        return this;
    }

    public int getQuantityCollection() {
        return quantityCollection;
    }

    public BookDto setQuantityCollection(int quantityCollection) {
        this.quantityCollection = quantityCollection;
        return this;
    }

    public int getAmountBorrowed() {
        return amountBorrowed;
    }

    public BookDto setAmountBorrowed(int amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public BookDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public static BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();

        return bookDto.setId(book.getId())
                .setTitle(book.getTitle())
                .setIsbn(book.getIsbn())
                .setEdition(book.getEdition())
                .setCategories(book.getCategories())
                .setAuthors(book.getAuthors())
                .setBusinessDayLoan(book.getBusinessDayLoan())
                .setDayFineValueLoan(book.getDayFineValueLoan())
                .setQuantityCollection(book.getQuantityCollection())
                .setAmountBorrowed((book.getAmountBorrowed()))
                .setCategory(book.getCategory());
    }

    public static List<BookDto> toDto(List<Book> books) {
        return books.stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("BookDto({id:%s, title:%s, isbn:%s, edition:%s, categories:%s, authors:%s, businessDayLoan:%s, dayFineValueLoan:%s, quantityCollection:%s, mountBorrowed: %s, category:%s})",
                this.getId(),
                this.getTitle(),
                this.getIsbn(),
                this.getEdition(),
                this.getCategories(),
                this.getAuthors(),
                this.getBusinessDayLoan(),
                this.getDayFineValueLoan(),
                this.getQuantityCollection(),
                this.getAmountBorrowed(),
                this.getCategory());
    }
}
