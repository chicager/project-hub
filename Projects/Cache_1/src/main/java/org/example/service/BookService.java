package org.example.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.example.model.Book;
import org.example.repository.BookRepository;

import java.util.concurrent.TimeUnit;

public class BookService {

    private final BookRepository bookRepository;

    // Простой кэш с ограничением по размеру
    private final Cache<Long, Book> simpleCache;

    // Кэш с автоматическим истечением срока действия
    private final Cache<Long, Book> expiringCache;

    // Кэш с автоматической загрузкой данных
    private final LoadingCache<Long, Book> loadingCache;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

        // Инициализация простого кэша
        this.simpleCache = Caffeine.newBuilder()
                .maximumSize(100)
                .build();

        // Инициализация кэша с истечением срока действия
        this.expiringCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        // Инициализация загружающего кэша
        this.loadingCache = Caffeine.newBuilder()
                .maximumSize(100)
                .build(bookRepository::findByid);
    }

    // Метод с использованием простого кэша
    public Book getBookWithSimpleCache(Long id) {
        return simpleCache.get(id, bookRepository::findByid);
    }

    // Метод с использованием кэша с истечением срока действия
    public Book getBookWithExpiringCache(Long id) {
        return expiringCache.get(id, bookRepository::findByid);
    }

    // Метод с использованием автозагружающего кэша
    public Book getBookWithLoadingCache(Long id) {
        return loadingCache.get(id);
    }

    public void invalidateCache(Long id) {
        simpleCache.invalidate(id);
        expiringCache.invalidate(id);
        loadingCache.invalidate(id);
    }
}
