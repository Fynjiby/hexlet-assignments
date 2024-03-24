package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping()
    public List<Comment> allComment(){
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable long id){
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Comment newComment(@RequestBody Comment data){
        commentRepository.save(data);
        return data;
    }

    @PutMapping("/{id}")
    public Comment editComment(@PathVariable long id, @RequestBody Comment data){
        var post = commentRepository.findById(id);
        if (post.isPresent()){
            data.setId(id);
            commentRepository.save(data);
        }else {
            throw new ResourceNotFoundException("not found");
        }
        return data;
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable long id){
        commentRepository.deleteById(id);
    }
}