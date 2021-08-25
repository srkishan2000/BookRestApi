package com.example.book.repository;

import com.example.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Find books by author name
     * @param author
     * @return {@link Collection <Book>}
     */
    Collection<Book> findBooksByAuthor(String author);

    /**
     * Find book by Title, Author and Year
     * @param title
     * @param author
     * @param year
     * @return {@link Book}
     */
    Book findBookByTitleAndAuthorAndYear(String title, String author, Integer year);
}
