package com.example.book.service;

import com.example.book.entity.Book;
import java.util.Collection;

public interface BookService {
    /**
     * Get all Books
     * @return {@link Collection< Book >}
     */
    Collection<Book> getBooks();

    /**
     * Get book by id has passed as an argument
     * @param id
     * @return {@link Book}
     */
    Book getBookById(Long id);

    /**
     * Get the books by author has passed as an argument
     * @param author
     * @return {@link Collection<Book>}
     */
    Collection<Book> getBooksByAuthor(String author);

    /**
     * Create a new book
     * @param book
     * @return {@link Book}
     */
    Book createBook(Book book);

    /**
     * Update a new book
     * @param book
     * @return {@link Book}
     */
    Book updateBook(Book book);

    /**
     * delete the book by id has passed as an argument
     * @param id
     */
    void deleteBook(Long id);

}