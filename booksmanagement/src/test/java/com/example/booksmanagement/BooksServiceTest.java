package com.example.booksmanagement;

import com.example.booksmanagement.entity.BookEntity;
import com.example.booksmanagement.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceTest {

@Mock private BooksRepository booksRepository;
@InjectMocks private BooksService booksService;


    @BeforeEach
    void setUp() {
        booksService = new BooksService(booksRepository);
    }

    @Test
    public void testAddNewBookShouldSaveBook() {
        BookEntity bookEntity = new BookEntity("book1","author1",230);
        when(booksRepository.findById(bookEntity.getBookName())).thenReturn(Optional.empty());
        BooksResponseEntity responseEntity = booksService.addNewBook(bookEntity);
        verify(booksRepository).save(bookEntity);
        assertEquals(201, responseEntity.getStatusCode());
        assertEquals("saved book 'book1'", responseEntity.getDescription());
    }

    @Test
    public void testAddNewBookShouldNotSaveBook() {
        BookEntity bookEntity = new BookEntity("book1","author1",230);
        when(booksRepository.findById(bookEntity.getBookName())).thenReturn(Optional.of(bookEntity));
        BooksResponseEntity responseEntity = booksService.addNewBook(bookEntity);
        verify(booksRepository, never()).save(bookEntity);
        assertEquals(304, responseEntity.getStatusCode());
        assertEquals("book 'book1' already exist!", responseEntity.getDescription());
    }

    @Test
    public void testAddNewBookWithDatabaseException() {
        BookEntity bookEntity = new BookEntity("book1","author1",230);
        Mockito.when(booksRepository.findById(Mockito.any())).thenThrow(new RuntimeException("Database error"));
        BooksResponseEntity response = booksService.addNewBook(bookEntity);
        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void testDeleteBookShouldNotDeleteBook() {
        String bookName = "Book";
        when(booksRepository.findById(bookName)).thenReturn(Optional.empty());
        BooksResponseEntity responseEntity = booksService.deleteBook(bookName);
        verify(booksRepository, never()).deleteById(bookName);
        assertEquals(304, responseEntity.getStatusCode());
        assertEquals("book 'Book' does not exist!", responseEntity.getDescription());
    }

    @Test
    public void testDeleteBookShouldDeleteBook() {
        BookEntity bookEntity = new BookEntity("Book","author1",230);
        when(booksRepository.findById(bookEntity.getBookName())).thenReturn(Optional.of(bookEntity));
        BooksResponseEntity responseEntity = booksService.deleteBook(bookEntity.getBookName());
        verify(booksRepository, times(1)).deleteById(bookEntity.getBookName());
        assertEquals(204, responseEntity.getStatusCode());
        assertEquals("deleted book 'Book'", responseEntity.getDescription());
    }

    @Test
    public void deleteBookWhenErrorOccursInDatabase() {
        BookEntity bookEntity = new BookEntity("book1","author1",230);
        Mockito.when(booksRepository.findById(bookEntity.getBookName())).thenThrow(new RuntimeException("Database error"));
        BooksResponseEntity response = booksService.deleteBook(bookEntity.getBookName());
        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void testGetBookShouldReturnBook() {
        BookEntity bookEntity = new BookEntity("Book","author1",230);
        when(booksRepository.findById(bookEntity.getBookName())).thenReturn(Optional.of(bookEntity));
        BookEntity returnedBook = booksService.getBook(bookEntity.getBookName());
        verify(booksRepository, times(1)).findById(bookEntity.getBookName());
        assertEquals(bookEntity, returnedBook);
    }

    @Test
    public void testGetBookShouldReturnNull() {
        String bookName = "Test Book";
        when(booksRepository.findById(bookName)).thenReturn(Optional.empty());
        BookEntity returnedBook = booksService.getBook(bookName);
        verify(booksRepository, times(1)).findById(bookName);
        assertNull(returnedBook);
    }
}