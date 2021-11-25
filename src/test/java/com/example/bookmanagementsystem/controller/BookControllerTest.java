package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.JsonUtil;
import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll_OK () throws Exception {
        List<Book> asList = Arrays.asList(new Book(1,101,"MK publisher","2nd edition", "IKIREZI Library", "After we collided"),
                new Book(2,102,"MK publisher","2nd edition", "IKIREZI Library", "After we collided"));
                when(bookServiceMock.getAll()).thenReturn(asList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/books/get-all")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"bookId\":101,\"name\":\"MK publisher\",\"edition\":\"2nd edition\",\"publisher\":\"IKIREZI Library\",\"stock\":\"After we collided\"}," +
                        "{\"id\":2,\"bookId\":102,\"name\":\"MK publisher\",\"edition\":\"2nd edition\",\"publisher\":\"IKIREZI Library\",\"stock\":\"After we collided\"}]"))
                .andReturn();
    }

    @Test
    public void getByOne_404() throws Exception {
        Book book = new Book(1,101,"MK publisher", "2nd edition",
                "IKIREZI Library", "After we collided");

        when(bookServiceMock.getBookById(book.getId())).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/books/all-books/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Book not found\"}"))
                .andReturn();

    }
//
    @Test
    public void getByOne_200() throws Exception {
        Book book = new Book(1,101,"MK publisher", "2nd edition",
                "IKIREZI Library", "After we collided");

        when(bookServiceMock.getBookById(book.getId())).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/books/all-books/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void getByOneBookId_404() throws Exception {
        Book book = new Book(1,101,"MK publisher", "2nd edition",
                "IKIREZI Library", "After we collided");

        when(bookServiceMock.getBookById(book.getBookId())).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/books/all-books/201")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Book not found\"}"))
                .andReturn();

    }

    @Test
    public void getByOneBookId_200() throws Exception {
        Book book = new Book(1,101,"MK publisher", "2nd edition",
                "IKIREZI Library", "After we collided");

        when(bookServiceMock.getBookById(book.getBookId())).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/books/all-books/101")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void createBookTest() throws Exception{
        Book book = new Book(2,202, "After","1st edition","MK publisher","Library Caritas");
        when(bookServiceMock.createBook(2,202, "After","1st edition","MK publisher","Library Caritas")).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/books/add-books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(book));

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateBookTest() throws Exception{
        when(bookServiceMock.updateBook(anyInt(), any(Book.class))).thenReturn(new Book(3,203, "Go Girl","1st edition","MK publisher","Library Caritas")
                ,new Book(4,204, "Feminism","1st edition","MK publisher","Library Caritas"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/books/edit-book/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{  \"name\":\"Science et vie\",\"edition\":\"1st edition\",\"publisher\":\"MK publisher\",\"stock\":\"library Caritas\"}");
        mockMvc.perform(request).andExpect(status().isAccepted()).andExpect(content().json("{\"id\":3, \"bookId\":203,\"name\":\"Go Girl\",\"edition\":\"1st edition\",\"publisher\":\"MK publisher\",\"stock\":\"Library Caritas\"}"));

    }

    @Test
    public void deleteBookTest() throws Exception{
        Book book = new Book(3,203, "Go Girl","1st edition","MK publisher","Library Caritas");
        doNothing().when(bookServiceMock).deleteBook(book.getId());
        mockMvc.perform(delete("/api/books/deleteBook/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


}
