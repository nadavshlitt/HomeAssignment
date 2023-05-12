package com.example.booksmanagement.repository;

import com.example.booksmanagement.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<BookEntity,String> {
}
