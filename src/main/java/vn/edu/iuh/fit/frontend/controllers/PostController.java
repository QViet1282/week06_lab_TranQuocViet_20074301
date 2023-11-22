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
        if (post.getPublished() == true)
            post.setPublishedAt(Instant.now());
        else
            post.setPublishedAt(null);
        postRepository.save(post);
        return "redirect:/posts/"+id+"/detail";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(HttpSession session, @PathVariable("id") String id){
        postRepository.deleteById(Long.parseLong(id));
        User user = (User) session.getAttribute("user");
        return "redirect:/users/profile/"+user.getId();
    }


    @GetMapping("/createPost")
    public String createPost(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "posts/create-post";
    }

    @PostMapping("/createPost")
    public String createPost(HttpSession session, @ModelAttribute Post post){
        User user = (User) session.getAttribute("user");
        post.setAuthor(user);
        post.setCreatedAt(Instant.now());
        post.setPublishedAt(Instant.now());
        postRepository.save(post);
        return "redirect:/users/profile/"+user.getId();
    }

    @GetMapping("/updatePost/{id}")
    public String updatePost(HttpSession session, @PathVariable("id") String id, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("post", postRepository.findById(Long.parseLong(id)).get());
        return "posts/update-post";
    }

    @PostMapping("/updatePost/{id}")
    public String updatePost(HttpSession session, @PathVariable("id") String id,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("summary") String summary,
                             @RequestParam("metaTitle") String metaTitle){
        User user = (User) session.getAttribute("user");
        Post post = postRepository.findById(Long.parseLong(id)).get();
        post.setUpdatedAt(Instant.now());
        post.setTitle(title);
        post.setContent(content);
        post.setSummary(summary);
        post.setMetaTitle(metaTitle);
        postRepository.save(post);
        return "redirect:/users/profile/"+user.getId();
    }
}
