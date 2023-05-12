package com.example.booksmanagement;

import com.example.booksmanagement.entity.BookEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache {
    private final Map<String, CacheEntry> cache;
    private final int maxSize = 3;
    private final ScheduledExecutorService executorService;

    public Cache() {
        cache = new LinkedHashMap<String, CacheEntry>(maxSize + 1, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheEntry> eldest) {
                return size() > maxSize;
            }
        };
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::expireCache, 1, 1, TimeUnit.SECONDS);//check every second if need to clean cache
    }

    /**
     * Add new element to cache
     * @param bookName key inside the hash
     * @param bookEntity data to be saved with key
     */
    public synchronized void put(String bookName, BookEntity bookEntity) {
        cache.put(bookName, new CacheEntry(bookEntity));
    }

    /**
     * Returns book data from cache by key
     * @param bookName key
     * @return BookEntity object with book details
     */
    public synchronized BookEntity get(String bookName) {
        CacheEntry entry = cache.get(bookName);
        return entry == null ? null : entry.getValue();
    }

    /**
     * The function is executed every second and decides if  need to delete cache element
     */
    private synchronized void expireCache() {
        long now = System.currentTimeMillis();
        cache.entrySet().removeIf(entry -> (now - entry.getValue().getTimestamp()) > TimeUnit.MINUTES.toMillis(1));
    }

    /**
     * kinda of wrap class that represent the cache entry.
     * this object contains the creation time of element and the book details.
     */
    private static class CacheEntry {
        private final BookEntity bookEntity;
        private final long timestamp;

        public CacheEntry(BookEntity bookEntity) {
            this.bookEntity = bookEntity;
            this.timestamp = System.currentTimeMillis();
        }

        public BookEntity getValue() {
            return bookEntity;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}