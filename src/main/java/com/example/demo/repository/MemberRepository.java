package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByEmail(String email);
    
    List<Member> findByActiveTrue();
    
    List<Member> findByMembershipType(String membershipType);
    
    List<Member> findByNameContainingIgnoreCase(String name);
}

// Made with Bob
