package com.example.book.service;

import com.example.book.BaseTest;
import com.example.book.entity.Book;
import com.example.book.exception.ResourceAlreadyExistsException;
import com.example.book.exception.ResourceNotFoundException;
import com.example.book.repository.BookRepository;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceImplTest extends BaseTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl service;

    @Before
    public void setUp() {
        reset(repository);
        mockBooks();
    }

    @Test
    public void getBooks() {
        when(repository.findAll()).thenReturn(Arrays.asList(mockBook1, mockBook2, mockBook3));
        MatcherAssert.assertThat("Verify Getting list of all books",
                service.getBooks(),
                containsInAnyOrder(
                        allOf(hasProperty("id", is(bookId1)),
                                hasProperty("title", is(title1)),
                                hasProperty("author", is(author1)),
                                hasProperty("year", is(year1))),
                        allOf(hasProperty("id", is(bookId2)),
                                hasProperty("title", is(title2)),
                                hasProperty("author", is(author2)),
                                hasProperty("year", is(year2))),
                        allOf(hasProperty("id", is(bookId3)),
                                hasProperty("title", is(title3)),
                                hasProperty("author", is(author2)),
                                hasProperty("year", is(year3)))));
    }

    @Test
    public void testGetBookById() {
        when(repository.findById(bookId1)).thenReturn(java.util.Optional.ofNullable(mockBook1));
        MatcherAssert.assertThat("Verify find the book by id",
                service.getBookById(bookId1),
                hasProperty("id", is(bookId1)));
    }

    @Test
    public void testResourceNotFoundWhenInvokeGetBookById() {
        when(repository.findById(bookId1)).thenReturn(java.util.Optional.empty());
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.getBookById(bookId1));
        Assertions.assertEquals("Book not found with id : " + bookId1, exception.getMessage());
    }

    @Test
    public void testGetBooksByAuthor() {
        when(repository.findBooksByAuthor(author1)).thenReturn(Collections.singletonList(mockBook1));
        MatcherAssert.assertThat("Verify find the book by author",
                service.getBooksByAuthor(author1),
                contains(allOf(
                        hasProperty("id", is(bookId1)),
                        hasProperty("author", is(author1)))));
    }

    @Test
    public void testResourceNotFoundWhenInvokeGetBooksByAuthor() {
        when(repository.findBooksByAuthor(author1)).thenReturn(null);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.getBooksByAuthor(author1));
        Assertions.assertEquals("Book not found with author : " + author1, exception.getMessage());
    }

    @Test
    public void testCreateBook() {
        Book newBook = new Book("newTitle", "newAuthor", 2050);
        when(repository.findBookByTitleAndAuthorAndYear(newBook.getTitle(), newBook.getAuthor(), newBook.getYear()))
                .thenReturn(null);
        when(repository.save(newBook)).thenReturn(mockBook1);
        MatcherAssert.assertThat("Verify that new book is added",
                service.createBook(newBook),
                allOf(hasProperty("id", is(bookId1)),
                        hasProperty("author", is(author1))));
    }

    @Test
    public void testResourceAlreadyExistsExceptionWhenInvokeCreateBook() {
        Book newBook = new Book(title1, author1, year1);
        when(repository.findBookByTitleAndAuthorAndYear(newBook.getTitle(), newBook.getAuthor(), newBook.getYear()))
                .thenReturn(mockBook1);

        Throwable exception = assertThrows(ResourceAlreadyExistsException.class, () -> service.createBook(newBook));
        Assertions.assertEquals("Resource Already Exists :" + mockBook1, exception.getMessage());
    }

    @Test
    public void testUpdateBook() {
        when(repository.findById(bookId1))
                .thenReturn(java.util.Optional.ofNullable(mockBook1));
        when(repository.save(mockBook1)).thenReturn(mockBook1);
        MatcherAssert.assertThat("Verify that book is updated",
                service.updateBook(mockBook1),
                allOf(hasProperty("id", is(bookId1)),
                        hasProperty("author", is(author1))));
    }

    @Test
    public void testResourceNotFoundWhenInvokeUpdateBook() {
        when(repository.findById(mockBook1.getId())).thenReturn(java.util.Optional.empty());
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.updateBook(mockBook1));
        Assertions.assertEquals("Book not found with id : " + mockBook1.getId(), exception.getMessage());
    }

    @Test
    public void testDeleteBook() {
        when(repository.findById(bookId1)).thenReturn(java.util.Optional.ofNullable(mockBook1));
        service.deleteBook(bookId1);
    }

    @Test
    public void testResourceNotFoundWhenInvokeDeleteBook() {
        when(repository.findById(bookId1)).thenReturn(java.util.Optional.empty());
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.deleteBook(bookId1));
        Assertions.assertEquals("Book not found with id : " + bookId1, exception.getMessage());
    }
}