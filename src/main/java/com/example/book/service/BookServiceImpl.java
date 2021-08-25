package com.example.book.service;

import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import com.example.book.exception.ResourceAlreadyExistsException;
import com.example.book.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Collection<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> bookFromDb = bookRepository.findById(id);
        if (bookFromDb.isPresent()) {
            log.info("Found {}", bookFromDb.get());
            return bookFromDb.get();
        } else {
            log.warn("Book not found with id : {}", id);
            throw new ResourceNotFoundException("Book not found with id : " + id);
        }
    }

    @Override
    public Collection<Book> getBooksByAuthor(String author) {
        Optional<Collection<Book>> bookCollectionFromDb = Optional.ofNullable(bookRepository.findBooksByAuthor(author));
        if (bookCollectionFromDb.isPresent() && bookCollectionFromDb.get().size() > 0) {
            log.info("Found {}", bookCollectionFromDb.get());
            return bookCollectionFromDb.get();
        } else {
            log.warn("Book not found with author : {}", author);
            throw new ResourceNotFoundException("Book not found with author : " + author);
        }
    }

    @Override
    public Book createBook(Book book) {
        Optional<Book> bookFromDb = Optional.ofNullable
                (this.bookRepository.findBookByTitleAndAuthorAndYear(
                        book.getTitle() != null ? book.getTitle().trim() : book.getTitle(),
                        book.getAuthor() != null ? book.getAuthor().trim() : book.getAuthor(),
                        book.getYear()));
        if (!bookFromDb.isPresent()) {
            log.info("Saving {}", book);
            return bookRepository.save(book);
        } else {
            log.warn("Resource Already Exists : {}", bookFromDb.get());
            throw new ResourceAlreadyExistsException("Resource Already Exists :" + bookFromDb.get());
        }
    }

    @Override
    public Book updateBook(Book book) {
        Optional<Book> bookFromDb = bookRepository.findById(book.getId());
        if (bookFromDb.isPresent()) {
            Book updatedBook = bookFromDb.get();
            BeanUtils.copyProperties(book, updatedBook);
            log.info("Updating {}", updatedBook);
            return bookRepository.save(updatedBook);
        } else {
            log.warn("Book not found with id : {}", book.getId());
            throw new ResourceNotFoundException("Book not found with id : " + book.getId());
        }
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book> bookFromDb = bookRepository.findById(id);
        if (bookFromDb.isPresent()) {
            Book deletedBook = bookFromDb.get();
            log.info("Deleting {}", deletedBook);
            bookRepository.delete(deletedBook);
        } else {
            log.warn("Book not found with id : {}", id);
            throw new ResourceNotFoundException("Book not found with id : " + id);
        }
    }
}