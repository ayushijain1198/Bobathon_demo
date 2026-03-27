package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Loan;
import com.example.demo.repository.LoanRepository;

@Service
@Transactional
public class LoanService {

    // This is a loan service class that provides methods to manage loans in the
    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;

    public LoanService(LoanRepository loanRepository, BookService bookService, MemberService memberService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public Loan createLoan(Loan loan) {
        // Verify book exists and is available
        bookService.getBookById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + loan.getBookId()));
        
        // Verify member exists and is active
        memberService.getMemberById(loan.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + loan.getMemberId()));
        
        // Set loan date and due date
        if (loan.getLoanDate() == null) {
            loan.setLoanDate(LocalDate.now());
        }
        if (loan.getDueDate() == null) {
            loan.setDueDate(LocalDate.now().plusDays(14)); // 14 days loan period
        }
        loan.setStatus("ACTIVE");
        
        // Decrease available copies
        bookService.decreaseAvailableCopies(loan.getBookId());
        
        return loanRepository.save(loan);
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByMember(Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }

    public List<Loan> getLoansByBook(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    public List<Loan> getActiveLoansByMember(Long memberId) {
        return loanRepository.findActiveLoansByMember(memberId);
    }

    public List<Loan> getActiveLoansByBook(Long bookId) {
        return loanRepository.findActiveLoansByBook(bookId);
    }

    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans(LocalDate.now());
    }

    // service/LoanService.java
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));
        
        if (!"ACTIVE".equals(loan.getStatus())) {
            throw new RuntimeException("Loan is not active");
        }
        
        loan.setReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");
        
        // Increase available copies
        bookService.increaseAvailableCopies(loan.getBookId());
        
        return loanRepository.save(loan);
    }

    // service/LoanService.java
    public void updateOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findOverdueLoans(LocalDate.now());
        for (Loan loan : overdueLoans) {
            loan.setStatus("OVERDUE");
            loanRepository.save(loan);
        }
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}

// Made with Bob
