package br.objective.training.library.dto.request;

import br.objective.training.library.entities.Book;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class BookRequestDto implements Serializable {
    @NotBlank
    @Size(max = 250)
    private String title;

    @NotBlank
    @Size(min = 10, max = 13)
    private String isbn;

    @Min(1)
    private int edition;

    @NotEmpty
    private List<String> categories;

    @NotEmpty
    private List<String> authors;

    @Min(1)
    private int businessDayLoan;

    @Min(1)
    private double dayFineValueLoan;

    @Min(1)
    private int quantityCollection;

    private String category = "normal";

    public String getTitle() {
        return title;
    }

    public BookRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookRequestDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public int getEdition() {
        return edition;
    }

    public BookRequestDto setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public BookRequestDto setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public BookRequestDto setAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public int getBusinessDayLoan() {
        return businessDayLoan;
    }

    public BookRequestDto setBusinessDayLoan(int businessDayLoan) {
        this.businessDayLoan = businessDayLoan;
        return this;
    }

    public double getDayFineValueLoan() {
        return dayFineValueLoan;
    }

    public BookRequestDto setDayFineValueLoan(double dayFineValueLoan) {
        this.dayFineValueLoan = dayFineValueLoan;
        return this;
    }

    public int getQuantityCollection() {
        return quantityCollection;
    }

    public BookRequestDto setQuantityCollection(int quantityCollection) {
        this.quantityCollection = quantityCollection;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public BookRequestDto setCategory(String category) {
        this.category = category;
        return this;
    }
}
