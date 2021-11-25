package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.Repository.BookRepository;
import com.example.bookmanagementsystem.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findAll_OK(){
        List<Book> books = bookRepository.findAll();
        assertEquals(1,books.size());
    }

    @Test
    public void findByOne_OK() throws Exception{
        Optional<Book> bookOption = bookRepository.findById(1);
        assertTrue(bookOption.isPresent());
    }

    @Test
    public void createBook() throws Exception{
        Book newBook =new Book(3,203, "Go Girl","1st edition","MK publisher","Library Caritas");
        Book result = bookRepository.save(newBook);
        assertTrue(result.getName() != null);
    }

    @Test
    public void deleteOne_success(){
        bookRepository.deleteById(1);
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

}
