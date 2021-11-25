package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.Repository.BookRepository;
import com.example.bookmanagementsystem.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepositoryMock;

    @InjectMocks
    private BookService bookService;

    @Test
    public void getAll_withSomeElements() {
        when(bookRepositoryMock.findAll())
                .thenReturn(Arrays.asList(new Book(1, 101, "MK publisher", "2nd edition", "IKIREZI Library", "After we collided")));
        assertEquals("MK publisher", bookService.getAll().get(0).getName());
    }

    @Test
    public  void createBook(){
        when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class))).thenReturn(new Book(2,202, "After","1st edition","MK publisher","Library Caritas"));
        assertEquals("After",bookService.createBook( 2,202, "After","1st edition","MK publisher","Library Caritas").getName());
//        assertEquals("M001",courseService.createCourse("M001","Math 1","Mathematics I", 1).getCode());
    }

    @Test
    public void deleteBook(){
        Book bo = new Book(3,202, "After","1st edition","MK publisher","Library Caritas");
        when(bookRepositoryMock.findById(bo.getId())).thenReturn(Optional.of(bo));

        bookService.deleteBook(bo.getId());

        verify(bookRepositoryMock).deleteById(bo.getId());

    }


    @Test
    public void updateBook(){
        Book book = new Book(2,202, "After","1st edition","MK publisher","Library Caritas");
        Book newBook = new Book(3,201, "After 3","1st edition","MK publisher","Library Caritas");
        given(bookRepositoryMock.findById(book.getId())).willReturn(Optional.of(book));

        bookService.updateBook(book.getId(),newBook);
        verify(bookRepositoryMock).save(newBook);
        verify(bookRepositoryMock).findById(book.getId());
    }
}
