package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.Repository.BookRepository;
import com.example.bookmanagementsystem.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public Book getBookById(int id) {
        Optional<Book> findByBookId = bookRepository.findById(id);
        if (findByBookId.isPresent()) {
            Book book = findByBookId.get();

            return book;
        }
        return null;
    }

    public Book createBook(Integer id, Integer bookId, String name, String edition, String publisher, String stock) {
        Book book = new Book(id, bookId, name, edition, publisher, stock);

        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));
        bookRepository.deleteById(id);
    }

    public Book updateBook(Integer id, Book book) {
        bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));

        book.setId(id);

        return bookRepository.save(book);

    }

    public Book detailBook(Integer id){
        return bookRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Book with id "+id+ " not found!"));
    }


}
