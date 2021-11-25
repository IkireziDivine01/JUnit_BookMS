package com.example.bookmanagementsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    private int id;
    private int BookId;
    private String name;
    private String edition;
    private String publisher;
    private String Stock;

    public Book() {
        super();
    }

    public Book(int id, int bookId, String name, String edition, String publisher, String stock) {
        this.id = id;
        this.BookId = bookId;
        this.name = name;
        this.edition = edition;
        this.publisher = publisher;
        this.Stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        this.BookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        this.Stock = stock;
    }

}
