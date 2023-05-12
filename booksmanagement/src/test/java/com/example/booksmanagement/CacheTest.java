package com.example.booksmanagement;

import com.example.booksmanagement.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;


class CacheTest {


    private Cache cache;
    private BookEntity book1;
    private BookEntity book2;
    private BookEntity book3;
    private BookEntity book4;

    @BeforeEach
    public void setUp() {
        cache = new Cache();
        book1 = new BookEntity("Book1", "Author1", 2021);
        book2 = new BookEntity("Book2", "Author2", 2022);
        book3 = new BookEntity("Book3", "Author3", 2023);
        book4 = new BookEntity("Book4", "Author4", 2024);
    }

    @Test
    public void testPutAndGet() {
        cache.put("Book1", book1);
        cache.put("Book2", book2);
        cache.put("Book3", book3);
        assertEquals(book1, cache.get("Book1"));
        assertEquals(book2, cache.get("Book2"));
        assertEquals(book3, cache.get("Book3"));
    }
    @Test
    public void testCacheEviction() throws InterruptedException {
        cache.put("Book1", book1);
        cache.put("Book2", book2);
        cache.put("Book3", book3);
        cache.put("Book4", book4);
        assertNull(cache.get("Book1"));
        assertEquals(book2, cache.get("Book2"));
        assertEquals(book3, cache.get("Book3"));
        assertEquals(book4, cache.get("Book4"));
        Thread.sleep(1000*62);        // Wait for cache to expire
        assertNull(cache.get("Book2"));
        assertNull(cache.get("Book3"));
        assertNull(cache.get("Book4"));
    }
}