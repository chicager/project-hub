package org.example.repository;

import org.example.model.Book;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BookRepository {

    private final Map<Long, Book> database = new HashMap<>();

    public BookRepository() {
        // Имитируем базу данных с некоторыми книгами
        database.put(1L, new Book(1L, "Война и Мир", "Лев Толстой", BigDecimal.valueOf(29.99)));
        database.put(2L, new Book(2L, "Преступление и наказание", "Федор Достоевский", new BigDecimal("24.99")));
        database.put(3L, new Book(3L, "Мастер и Маргарита", "Михаил Булгаков", new BigDecimal("19.99")));
    }

    public Book findByid(Long id) {
        // Имитируем задержку базы данных
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return database.get(id);
    }
}
