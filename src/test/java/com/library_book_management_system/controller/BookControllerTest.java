package com.library_book_management_system.controller;

import com.library_book_management_system.model.Book;
import com.library_book_management_system.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    void whenGetBooks_thenReturnIsOk() {
        when(bookService.getBooks()).thenReturn(construcMockBookList());

        ResponseEntity<List<Book>> response = bookController.getBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenGetBooks_thenReturnNoContent() {
        when(bookService.getBooks()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Book>> response = bookController.getBooks();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void whenGetBooks_thenReturnInternalServerError() throws Exception {
        when(bookService.getBooks()).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<List<Book>> response = bookController.getBooks();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void whenGetBook_thenReturnIsOk() {
        when(bookService.getBookById(anyLong())).thenReturn(construcMockBook());
        ResponseEntity<Book> response = bookController.getBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenGetBook_thenReturnNotFound() {
        when(bookService.getBookById(anyLong())).thenReturn(null);
        ResponseEntity<Book> response = bookController.getBook(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void whenGetBook_thenReturnInternalServerError() {
        when(bookService.getBookById(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<Book> response = bookController.getBook(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void whenAddBook_thenReturnIsOk() {
        when(bookService.addBook(any(Book.class))).thenReturn(construcMockBook());
        ResponseEntity<Book> response = bookController.addBooks(construcMockBook());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void whenAddBook_thenReturnBadRequest() {
        when(bookService.addBook(any(Book.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        ResponseEntity<Book> response = bookController.addBooks(construcMockBook());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void whenUpdateBook_thenReturnIsOk() {
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(construcMockBook());
        ResponseEntity<Book> response = bookController.updateBooks(1L, construcMockBook());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUpdateBook_thenReturnBadRequest() {
        when(bookService.updateBook(anyLong(), any(Book.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        ResponseEntity<Book> response = bookController.updateBooks(5L, construcMockBook());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void whenDeleteBook_thenReturnIsOk() {
        bookService.deleteBook(construcMockBook().getId());
        verify(bookService, times(1)).deleteBook(eq(construcMockBook().getId()));
        ResponseEntity<String> response = bookController.deleteBook(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenDeleteBook_thenReturnBadRequest() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(bookService).deleteBook(anyLong());
        ResponseEntity<String> response = bookController.deleteBook(5L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Book construcMockBook() {
        return new Book(1L, "0000001", "J. K. Rowling", "Harry Potter and the Chamber of Secrets", LocalDate.parse("1998-05-06"));
    }

    private List<Book> construcMockBookList() {
        Book book1 = new Book(1L, "0000001", "J. K. Rowling", "Harry Potter and the Chamber of Secrets", LocalDate.parse("1998-05-06"));
        Book book2 = new Book(2L, "0000002", "J. K. Rowling", "Harry Potter and the Prisoner of Azkaban", LocalDate.parse("1999-05-06"));
        return List.of(book1, book2);
    }
}
