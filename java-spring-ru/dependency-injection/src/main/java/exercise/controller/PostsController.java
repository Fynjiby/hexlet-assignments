package exercise.controller;

import exercise.repository.CommentRepository;
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

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

@RequestMapping("/posts")
@RestController
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping()
    public List<Post> allPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable long id){
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Post newPost(@RequestBody Post data){
        postRepository.save(data);
        return data;
    }

    @PutMapping("/{id}")
    public Post editPost(@PathVariable long id, @RequestBody Post data){
        var post = postRepository.findById(id);
        if (post.isPresent()){
            data.setId(id);
            postRepository.save(data);
        }else {
            throw new ResourceNotFoundException("not found");
        }
        return data;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable long id){
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }
}
