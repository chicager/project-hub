package org.example;

import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;

public class CachingDemo {

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        BookService bookService = new BookService(bookRepository);

        // Демонстрация простого кэша
        System.out.println("+--------------------------------+");
        System.out.println("Демонстрация простого кэша:");
        System.out.println("+--------------------------------+");
        System.out.println("Первый запрос (без кэша):");
        long startTime = System.currentTimeMillis();
        Book book1 = bookService.getBookWithSimpleCache(1L);
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - startTime) + "мс");
        System.out.println("Книга: " + book1);

        System.out.println("\nВторой запрос (с кэшем):");
        startTime = System.currentTimeMillis();
        book1 = bookService.getBookWithSimpleCache(1L);
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - startTime) + "мс");
        System.out.println("Книга: " + book1);

        // Демонстрация автозагружающего кэша
        System.out.println("\n+--------------------------------+");
        System.out.println("Демонстрация автозагружающего кэша:");
        System.out.println("+--------------------------------+");
        System.out.println("Первый запрос:");
        startTime = System.currentTimeMillis();
        Book book2 = bookService.getBookWithLoadingCache(2L);
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - startTime) + "мс");
        System.out.println("Книга: " + book2);

        System.out.println("\nВторой запрос (с кэшем):");
        startTime = System.currentTimeMillis();
        book2 = bookService.getBookWithLoadingCache(2L);
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - startTime) + "мс");
        System.out.println("Книга: " + book2);

        // Очистка кэша
        System.out.println("\nОчистка кэша для книги с ID 1");
        bookService.invalidateCache(1L);

        System.out.println("\nЗапрос после очистки кэша:");
        startTime = System.currentTimeMillis();
        book1 = bookService.getBookWithSimpleCache(1L);
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - startTime) + "мс");
        System.out.println("Книга: " + book1);
    }
}