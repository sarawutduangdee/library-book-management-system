package com.library_book_management_system.controller;

import com.library_book_management_system.model.Book;
import com.library_book_management_system.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> books = bookService.getBooks();
            if (books.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id);
            if (book == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<String> addBooks(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return new ResponseEntity<>("SAVE_SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("SAVE_ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBooks(@PathVariable Long id,
                                              @RequestBody Book book) {
        try {
            bookService.updateBook(id, book);
            return new ResponseEntity<>("UPDATED_SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("UPDATED_ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>("DELETED_SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("DELETED_ERROR", HttpStatus.BAD_REQUEST);

        }
    }
}
