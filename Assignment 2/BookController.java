package com.example.bookstore_mongodb.controller;

import com.example.bookstore_mongodb.entity.Book;
import com.example.bookstore_mongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteBook(
            @PathVariable String id
    ) {
        boolean delete = bookService.deleteBook(id);
        if (delete) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Book deleted."));
        } else {
            return ResponseEntity.notFound().build();
        }


    }

}


