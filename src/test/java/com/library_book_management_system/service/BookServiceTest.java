package com.library_book_management_system.service;

import com.library_book_management_system.model.Book;
import com.library_book_management_system.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenFindBookAll_thenReturnBooks() {

        var construcMockBook = new Book();
        when(bookRepository.findAll()).thenReturn(List.of(construcMockBook));

        var books = bookService.getBooks();
        assertTrue(!books.isEmpty());
    }

    @Test
    void whenFindBookAll_thenReturnEmptyBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(new Book()));

        var books = bookService.getBooks();
        assertFalse(books.isEmpty());
    }

    @Test
    void whenFindBookById_thenReturnBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(construcMockBook()));

        Book book = bookService.getBookById(1L);
        assertEquals(book.getId(), construcMockBook().getId());
    }

    @Test
    void whenFindBookById_thenReturnEmptyBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        var book = bookService.getBookById(0L);
        assertNull(book);
    }

    @Test
    void whenAddBook_thenReturnSuccess() {
        when(bookRepository.save(any(Book.class))).thenReturn(construcMockBook());
        Book book = bookService.addBook(construcMockBook());

        assertEquals(book.getId(), construcMockBook().getId());

    }

    @Test
    void whenUpdateBook_thenReturnSuccess() {
        Book updateBook = construcMockBook();
        updateBook.setIsbn("0003215");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(construcMockBook()));
        when(bookRepository.save(any(Book.class))).thenReturn(updateBook);
        Book book = bookService.updateBook(1L, updateBook);
        assertEquals(book.getId(), updateBook.getId());
        assertEquals(book.getIsbn(), updateBook.getIsbn());
    }

    @Test
    void whenUpdateBook_thenReturnNull() {

        Book updateBook = construcMockBook();
        updateBook.setIsbn("0003215");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        Book book = bookService.updateBook(5L, updateBook);
        assertNull(book);
    }

    @Test
    void whenDeleteBook_thenReturnSuccess() {
        bookService.deleteBook(construcMockBook().getId());
        verify(bookRepository, times(1)).deleteById(eq(construcMockBook().getId()));
    }

    private Book construcMockBook() {
        return new Book(1L, "0000001", "J. K. Rowling", "Harry Potter and the Chamber of Secrets", LocalDate.parse("1998-05-06"));

    }
}
