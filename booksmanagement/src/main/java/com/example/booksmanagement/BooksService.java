package com.example.booksmanagement;

import com.example.booksmanagement.entity.BookEntity;
import com.example.booksmanagement.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BooksService {

    private Cache cache=new Cache();

    @Autowired
    private BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository=booksRepository;
    }

    /**
     * this function responsible for adding new books
     * @param bookEntity BookEntity object the represents the book to be saved
     * @return BooksResponseEntity object that contains status code and description
     */
    public BooksResponseEntity addNewBook(@RequestBody BookEntity bookEntity){
        try {
            if(!booksRepository.findById(bookEntity.getBookName()).isEmpty()){
                //if book already exist
                return new BooksResponseEntity(304 ,String.format("book '%s' already exist!",bookEntity.getBookName()));
            }else{
                //save new book
                booksRepository.save(bookEntity);
                return new BooksResponseEntity(201,String.format("saved book '%s'",bookEntity.getBookName()));
            }
        }catch (Exception e){
            //error with db
            return new BooksResponseEntity(400,e.toString());
        }
    }

    /**
     * this function responsible for deleting books
     * @param bookName book's name to be deleted
     * @return BooksResponseEntity object that contains status code and description
     */
    public BooksResponseEntity deleteBook(String bookName){
        try {
            if(booksRepository.findById(bookName).isEmpty()){
                //if book already exist
                return new BooksResponseEntity(304 ,String.format("book '%s' does not exist!",bookName));
            }else{
                //save new book
                booksRepository.deleteById(bookName);
                return new BooksResponseEntity(204,String.format("deleted book '%s'",bookName));
            }
        }catch (Exception e){
            //error with db
            return new BooksResponseEntity(400,e.toString());
        }
    }

    /**
     * this function responsible for retrieve book's details
     * @param bookName the book for which we would like to receive information
     * @return BookEntity object that contains book details if the book exist otherwise null
     */
    public BookEntity getBook(String bookName){
        try {
            BookEntity bookEntity = booksRepository.findById(bookName).get();
            cache.put(bookEntity.getBookName(),bookEntity);
            return bookEntity;
        }catch (Exception e){
            //error with db
            return null;
        }
    }

    /**
     * this function responsible for retrieve book's details from cache
     * @param bookName book's name
     * @return BookEntity object that contains book details if the book exist otherwise null
     */
    public BookEntity getBookFromCache(String bookName){
     return cache.get(bookName);
    }
}
