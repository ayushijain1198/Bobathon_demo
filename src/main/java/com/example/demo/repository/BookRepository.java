package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByCategory(String category);
    
    List<Book> findByAvailableTrue();
    
    @Query("SELECT b FROM Book b WHERE b.available = true AND b.availableCopies > 0")
    List<Book> findAvailableBooks();
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    List<Book> findByIsbn(String isbn);
}

// Made with Bob
