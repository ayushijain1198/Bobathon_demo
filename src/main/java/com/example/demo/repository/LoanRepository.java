package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    List<Loan> findByMemberId(Long memberId);
    
    List<Loan> findByBookId(Long bookId);
    
    List<Loan> findByStatus(String status);
    
    @Query("SELECT l FROM Loan l WHERE l.status = 'ACTIVE' AND l.dueDate < :currentDate")
    List<Loan> findOverdueLoans(LocalDate currentDate);
    
    @Query("SELECT l FROM Loan l WHERE l.memberId = :memberId AND l.status = 'ACTIVE'")
    List<Loan> findActiveLoansByMember(Long memberId);
    
    @Query("SELECT l FROM Loan l WHERE l.bookId = :bookId AND l.status = 'ACTIVE'")
    List<Loan> findActiveLoansByBook(Long bookId);
}

// Made with Bob
