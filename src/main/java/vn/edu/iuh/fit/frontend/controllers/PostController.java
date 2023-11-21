package vn.edu.iuh.fit.frontend.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.PostComment;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.PostCommentRepository;
import vn.edu.iuh.fit.backend.repositories.PostRepository;
import vn.edu.iuh.fit.backend.services.PostServices;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;
    private PostServices postServices = new PostServices(postRepository);
    @GetMapping("/{id}/detail")
    public String showPost(HttpSession session, @PathVariable("id") String id, Model model){
        model.addAttribute("post", postRepository.findById(Long.parseLong(id)).get());
        List<PostComment> postComments = postCommentRepository.findAllByPublishedIsTrueAndPostId(Long.parseLong(id));
        System.out.println(postComments);
        model.addAttribute("commentsByPost", postComments);
        User user = (User) session.getAttribute("user");
        model.addAttribute("loggedInUserId", user.getId());
        return "posts/post-detail";
    }

    @PostMapping("/addComment/{id}")
    public String addComment(HttpSession session,
                             @PathVariable("id") String id,
                             @ModelAttribute PostComment postComment){
        User user = (User) session.getAttribute("user");
        postComment.setPublished(true);
        postComment.setPost(postRepository.findById(Long.parseLong(id)).get());
        postComment.setUser(user);
        postComment.setCreatedAt(Instant.now());
        postComment.setPublishedAt(Instant.now());
        postComment.setParent(null);
        postCommentRepository.save(postComment);
        return "redirect:/posts/"+id+"/detail";
    }

    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") String id){
        PostComment postComment = postCommentRepository.findById(Long.parseLong(id)).get();
        postComment.setPublished(false);
        postCommentRepository.save(postComment);
        return "redirect:/posts/"+postComment.getPost().getId()+"/detail";
    }

    @GetMapping("/publishPost/{id}")
    public String publishPost(@PathVariable("id") String id){
        Post post = postRepository.findById(Long.parseLong(id)).get();
        post.setPublished(!post.getPublished());
        postRepository.save(post);
        return "redirect:/posts/"+id+"/detail";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(HttpSession session, @PathVariable("id") String id){
        postRepository.deleteById(Long.parseLong(id));
        User user = (User) session.getAttribute("user");
        return "redirect:/users/profile/"+user.getId();
    }

}
