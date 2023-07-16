package br.objective.training.library.service;

import br.objective.training.library.dto.request.BookRequestDto;
import br.objective.training.library.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto create(BookRequestDto bookRequestDto);

    BookDto update(Long id, BookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto delete(Long id);
}
