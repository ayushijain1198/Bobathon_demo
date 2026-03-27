package com.example.demo.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Book;
import com.example.demo.entity.Member;
import com.example.demo.service.BookService;
import com.example.demo.service.MemberService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookService bookService;
    private final MemberService memberService;

    public DataInitializer(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @Override
    public void run(String... args) {
        // Initialize Books
        Book book1 = new Book(null, "Clean Code", "Robert C. Martin", "978-0132350884", "Programming", true, 3, 3);
        Book book2 = new Book(null, "Design Patterns", "Gang of Four", "978-0201633610", "Programming", true, 2, 2);
        Book book3 = new Book(null, "The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", "Programming", true, 5, 5);
        Book book4 = new Book(null, "Effective Java", "Joshua Bloch", "978-0134685991", "Programming", true, 4, 4);
        Book book5 = new Book(null, "Introduction to Algorithms", "Thomas H. Cormen", "978-0262033848", "Computer Science", true, 2, 2);

        bookService.createBook(book1);
        bookService.createBook(book2);
        bookService.createBook(book3);
        bookService.createBook(book4);
        bookService.createBook(book5);

        // Initialize Members
        Member member1 = new Member(null, "John Doe", "john.doe@example.com", "123-456-7890", LocalDate.now().minusMonths(6), "PREMIUM", true);
        Member member2 = new Member(null, "Jane Smith", "jane.smith@example.com", "098-765-4321", LocalDate.now().minusMonths(3), "REGULAR", true);
        Member member3 = new Member(null, "Bob Johnson", "bob.johnson@example.com", "555-123-4567", LocalDate.now().minusMonths(1), "REGULAR", true);
        Member member4 = new Member(null, "Alice Williams", "alice.williams@example.com", "555-987-6543", LocalDate.now().minusYears(1), "PREMIUM", true);

        memberService.createMember(member1);
        memberService.createMember(member2);
        memberService.createMember(member3);
        memberService.createMember(member4);

        System.out.println("Sample data initialized successfully!");
    }
}

// Made with Bob
