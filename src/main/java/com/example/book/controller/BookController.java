package com.example.book.controller;

import com.example.book.entity.Book;
import com.example.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1")  // Root mapping for Book Api
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);
    private final String rootURI = "/api/v1";

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        log.info("Invoked - {}/book/{}", rootURI, id);
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping("/books/{author:.+}")
    public ResponseEntity<Collection<Book>> getBooks(@PathVariable("author") String author) {
        log.info("Invoked - {}/books/{}", rootURI, author);
        return new ResponseEntity<>(bookService.getBooksByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<Collection<Book>> getBooks() {
        log.info("Invoked - {}/books", rootURI);
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping(value = "/createbook",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
        log.info("Invoked - {}/createbook", rootURI);
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @PutMapping(value = "/updatebook",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) {
        log.info("Invoked - {}/updatebook", rootURI);
        return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id) {
        log.info("Invoked - {}/deletebook/{}", rootURI, id);
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}