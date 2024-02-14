package com.example.securityproject.controllers;

import com.example.securityproject.dto.CommentDto;
import com.example.securityproject.dto.MessageDto;
import com.example.securityproject.services.interfaces.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<CommentDto>> getALl(){
        return ResponseEntity.ok(this.commentService.getAll());
    }

    @PostMapping("")
    public ResponseEntity<CommentDto> add(@Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.commentService.save(commentDto));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto){
        commentDto.setId(id);
        return ResponseEntity.ok(this.commentService.save(commentDto));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> delete(@RequestParam Long id){
        return ResponseEntity.ok("Comment deleted successfully !");
    }
}
