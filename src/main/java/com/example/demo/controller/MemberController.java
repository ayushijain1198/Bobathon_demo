package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        return memberService.getMemberByEmail(email)
                .map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Member>> getActiveMembers() {
        List<Member> members = memberService.getActiveMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Member>> getMembersByType(@PathVariable String type) {
        List<Member> members = memberService.getMembersByType(type);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembersByName(@RequestParam String name) {
        List<Member> members = memberService.searchMembersByName(name);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        try {
            Member updatedMember = memberService.updateMember(id, member);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateMember(@PathVariable Long id) {
        try {
            memberService.deactivateMember(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateMember(@PathVariable Long id) {
        try {
            memberService.activateMember(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

// Made with Bob
