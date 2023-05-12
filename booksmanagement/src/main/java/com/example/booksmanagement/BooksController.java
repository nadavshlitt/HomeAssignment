package com.example.booksmanagement;

import com.example.booksmanagement.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/books")
@Slf4j
public class BooksController {

    @Autowired
    private BooksService booksService;


    @PostMapping
    public BooksResponseEntity addNewBook(@RequestBody BookEntity bookEntity){
        log.info(String.format("executing function addNewBook      payload:%s ",bookEntity));
        return booksService.addNewBook(bookEntity);
    }

    @DeleteMapping(path = "{bookName}")
    public BooksResponseEntity deleteBook(@PathVariable String bookName){
        log.info(String.format("executing function deleteBook      payload:{bookName=%s} ",bookName));

        return booksService.deleteBook(bookName);
    }

    @GetMapping(path = "{bookName}")
    public BookEntity getBook(@PathVariable String bookName){
        log.info(String.format("executing function getBook      payload:{bookName=%s} ",bookName));
        return booksService.getBook(bookName);
    }
    @GetMapping(path = "cache/{bookName}")
    public BookEntity getBookFromCache(@PathVariable String bookName) {
        log.info(String.format("executing function getBookFromCache      payload:{bookName=%s} ",bookName));
        return booksService.getBookFromCache(bookName);
    }
}
