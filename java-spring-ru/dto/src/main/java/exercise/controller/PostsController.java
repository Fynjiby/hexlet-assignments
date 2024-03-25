package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping()
    public List<PostDTO> getPostDTOList(){
        var posts = postRepository.findAll();
        var result = posts.stream().map(this::postToDTO).toList();
        return result;
    }

    @GetMapping("/{id}")
    public PostDTO getPostDTO(@PathVariable long id){
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
        return postToDTO(post);
    }

    private PostDTO postToDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(commentRepository.findByPostId(post.getId()).stream().map(this::commentToDTO).toList());
        return dto;
    }

    private CommentDTO commentToDTO(Comment comment){
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }

}