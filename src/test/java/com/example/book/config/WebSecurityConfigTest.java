package com.example.book.config;
import com.example.book.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSecurityConfigTest {

    private final String
            userName = "user",
            password = "password",
            blankUserName = " ",
            blankPassword = " ";

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testHelloCallerWithoutAuthorizationIsOK() {
        ResponseEntity<String> result = template.getForEntity("/hello/John", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testBasicAuthenticationWithUserNameAndPassword() {
        ResponseEntity<Book> result = template.withBasicAuth(userName, password)
                .getForEntity("/api/v1/book/1000000", Book.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testUnAuthorizedWhenBlankPassword() {
        ResponseEntity<Book> result = template.withBasicAuth(userName, blankPassword)
                .getForEntity("/api/v1/book/1000000", Book.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUnAuthorizedWhenBlankUserName() {
        ResponseEntity<Book> result = template.withBasicAuth(blankUserName, password)
                .getForEntity("/api/v1/book/1000000", Book.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUnAuthorizedWhenBlankUserNameAndBlankPassword() {
        ResponseEntity<Book> result = template.withBasicAuth(blankUserName, blankPassword)
                .getForEntity("/api/v1/book/1000000", Book.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}