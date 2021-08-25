package com.example.book.controller;


import com.example.book.BaseTest;
import com.example.book.entity.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest extends BaseTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        reset(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.getBookById(bookId1)).thenReturn(mockBook1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/book/" + bookId1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(author1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(year1));
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        when(bookService.getBooksByAuthor(author2)).thenReturn(Arrays.asList(mockBook2, mockBook3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/books/" + author2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bookId2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(title2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(author2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value(year2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(bookId3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(title3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value(author2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value(year3));
    }

    @Test
    public void testGetBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(Arrays.asList(mockBook1, mockBook2, mockBook3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bookId1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(title1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(author1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value(year1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(bookId2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(title2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value(author2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value(year2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(bookId3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value(title3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].author").value(author2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].year").value(year3));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book newBook = new Book(null,"title", "author", 2021);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/createbook")
                        .content(asJsonString(newBook))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(bookService).createBook(any(Book.class)); // I think we could have use Argument captor to check the parameters of the arguments and its values
    }

    @Test
    public void testUpdateBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/updatebook")
                        .content(asJsonString(mockBook1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService).updateBook(any(Book.class)); // I think we could have use Argument captor to check the parameters of the arguments and its values
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/deletebook/"+bookId1))
                .andExpect(status().isAccepted());
        verify(bookService).deleteBook(eq(bookId1));
    }

    @Test
    public void tesUrlNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/booking"))
                .andExpect(status().isNotFound());
    }
}