package com.example.book;
import com.example.book.entity.Book;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.reset;

public abstract class BaseTest {
    protected final Long bookId1 = 100L, bookId2 = 200L, bookId3 = 300L;
    protected final String title1 = "Title1", title2 = "Title2", title3 = "Title3",
            author1 = "Author1", author2 = "Author2";
    protected final Integer year1 = 2001, year2 = 2002, year3 = 2003;

    @Mock
    protected Book mockBook1, mockBook2, mockBook3;

    @Before
    public void setUp() {
        reset(mockBook1, mockBook2, mockBook3);
    }

    protected void mockBooks() {
        mockBook1 = new Book();
        mockBook1.setId(bookId1);
        mockBook1.setTitle(title1);
        mockBook1.setAuthor(author1);
        mockBook1.setYear(year1);

        mockBook2 = new Book();
        mockBook2.setId(bookId2);
        mockBook2.setTitle(title2);
        mockBook2.setAuthor(author2);
        mockBook2.setYear(year2);

        mockBook3 = new Book();
        mockBook3.setId(bookId3);
        mockBook3.setTitle(title3);
        mockBook3.setAuthor(author2);
        mockBook3.setYear(year3);
    }
}
