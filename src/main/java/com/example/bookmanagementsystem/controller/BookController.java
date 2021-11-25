package com.example.bookmanagementsystem.controller;


import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.service.BookService;
import com.example.bookmanagementsystem.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Formatter;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/get-all")
        public List<Book> getAll(){
            return bookService.getAll();
        }

    @GetMapping("/all-books/{id}")
        public ResponseEntity<?> getBookById(@PathVariable(name = "id") int id){
            Book book = bookService.getBookById(id);
            if(book != null){
                return ResponseEntity.ok(book);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Book not found"));
        }

    @PostMapping("/add-books")
    public Book addBook(@RequestBody Book book){
        return bookService.createBook(book.getId(), book.getBookId(), book.getName(), book.getEdition(), book.getPublisher(), book.getStock());
    }

    @PutMapping("/edit-book/{id}")
    public ResponseEntity<?> editBook(@PathVariable int id,@RequestBody Book book ){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookService.updateBook(id,book));
    }



    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id){
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Book removed");
    }

    }

