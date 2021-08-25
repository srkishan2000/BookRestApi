package com.example.book.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class HelloControllerTest {

    @InjectMocks
    private HelloController helloController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }

    @Test
    public void testHelloCaller() throws Exception {
        String callerName = "John";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hello/"+callerName))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, John!"));
    }
}