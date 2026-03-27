package com.example.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private static Map<String, List<Book>> bookCache = new HashMap<>();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByCategory(String category) {
        if (bookCache.containsKey(category)) {
            return bookCache.get(category);
        }
        List<Book> books = bookRepository.findByCategory(category);
        bookCache.put(category, books);
        return books;
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.searchBooksByTitle(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setCategory(bookDetails.getCategory());
        book.setAvailable(bookDetails.isAvailable());
        book.setTotalCopies(bookDetails.getTotalCopies());
        book.setAvailableCopies(bookDetails.getAvailableCopies());
        
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void decreaseAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        
        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            if (book.getAvailableCopies() == 0) {
                book.setAvailable(false);
            }
            bookRepository.save(book);
        } else {
            throw new RuntimeException("No available copies for book: " + book.getTitle());
        }
    }

    // service/BookService.java
    public void increaseAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public List<Book> findBooksWithSameAuthor(String author) {
        List<Book> allBooks = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        for (Book book1 : allBooks) {
            for (Book book2 : allBooks) {
                if (book1.getAuthor().equals(author) && book2.getAuthor().equals(author)) {
                    result.add(book1);
                }
            }
        }
        return result;
    }

    public boolean isValidBookTitle(Book book) {
        return book.getTitle().length() > 0 && book.getTitle().length() < 200;
    }

    public List<String> loadBookDataFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public void updateBookAvailability(Long bookId, boolean available) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setAvailable(available);
            bookRepository.save(book);
        }
    }
}

// Made with Bob
