package com.example.booksmanagement;

public class BooksResponseEntity {

    private int statusCode;
    private String description;

    public BooksResponseEntity() {
    }

    public BooksResponseEntity(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BooksResponseEntity{" +
                "statusCode=" + statusCode +
                ", description='" + description + '\'' +
                '}';
    }
}
