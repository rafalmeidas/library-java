package br.objective.training.library.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false ,length = 255)
    private String title;
    @Column(name = "ISBN", nullable = false ,length = 13)
    private String isbn;

    @Column(name = "EDITION", nullable = false)
    private int edition;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "BOOK_CATEGORY", joinColumns = @JoinColumn(name = "ID_LIVRO"))
    @Column(nullable = false, length = 255)
    private List<String> categories;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "BOOK_AUTHORS", joinColumns = @JoinColumn(name = "ID_LIVRO"))
    @Column(nullable = false, length = 255)
    private List<String> authors;

    @Column(name = "BUSINESS_DAY_LOAN",nullable = false, length = 255)
    private int businessDayLoan;

    @Column(name = "DAY_FINE_VALUE_LOAN", nullable = false)
    private double dayFineValueLoan;

    @Column(name = "QUANTITY_COLLECTION", nullable = false)
    private int quantityCollection;

    @Column(name = "AMOUNT_BORROWED", nullable = true)
    private int amountBorrowed;

    @Column(name = "CATEGORY",nullable = false, length = 255)
    private String category;

    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public int getEdition() {
        return edition;
    }

    public Book setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Book setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public Book setAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public int getBusinessDayLoan() {
        return businessDayLoan;
    }

    public Book setBusinessDayLoan(int businessDayLoan) {
        this.businessDayLoan = businessDayLoan;
        return this;
    }

    public double getDayFineValueLoan() {
        return dayFineValueLoan;
    }

    public Book setDayFineValueLoan(double dayFineValueLoan) {
        this.dayFineValueLoan = dayFineValueLoan;
        return this;
    }

    public int getQuantityCollection() {
        return quantityCollection;
    }

    public Book setQuantityCollection(int quantityCollection) {
        this.quantityCollection = quantityCollection;
        return this;
    }

    public int getAmountBorrowed() {
        return amountBorrowed;
    }

    public Book setAmountBorrowed(int amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Book setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return edition == book.edition &&
                businessDayLoan == book.businessDayLoan &&
                Double.compare(book.dayFineValueLoan, dayFineValueLoan) == 0 &&
                quantityCollection == book.quantityCollection &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(categories, book.categories) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, edition, categories, authors, businessDayLoan, dayFineValueLoan, quantityCollection, category);
    }
}
