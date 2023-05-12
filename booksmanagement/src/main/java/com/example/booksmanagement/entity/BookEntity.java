package com.example.booksmanagement.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "BookEntity")
@Table(name = "books")
public class BookEntity {

    @Id
    @Column(
            name = "book_name",
            nullable = false
    )
    private String bookName;
    @Column(
            name = "book_author",
            nullable = false
    )
    private String bookAuthor;
    @Column(
            name = "number_of_pages",
            nullable = false
    )
    private int numberOfPages;

    public BookEntity() {
    }

    public BookEntity(String bookName, String bookAuthor, int numberOfPages) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.numberOfPages = numberOfPages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }
}
