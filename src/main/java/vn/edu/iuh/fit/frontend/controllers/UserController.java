package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.PostRepository;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
import vn.edu.iuh.fit.backend.services.UserServices;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") String id){
        return "";
    }

//    @GetMapping("/add-form")
//    public String show(@ModelAttribute User user, Model model){
//        user =new User();
//        model.addAttribute("user",user);
//        return "users/add-form";
//    }
//    public String add(@ModelAttribute User user, Model model){
//        userRepository.save(user);
//        return "/";
//    }

    @GetMapping("/login-form")
    public String login(){
        return "users/login-form";
    }

    @GetMapping("/register-form")
    public String register(){
        return "users/register-form";
    }


    @PostMapping("/login")
    public String login (HttpSession session, @RequestParam("emailorphone") String emailorphone,
                         @RequestParam("password") String password){
        List<User> users = userRepository.findAll();
        if (emailorphone == null || password == null){
            System.out.println("Login fail");
            return "users/login-form";
        }
        for (User user: users){
            if (user.getEmail().equals(emailorphone) || user.getMobile().equals(emailorphone)){
                if (user.getPasswordHash().equals(userServices.encrypt(password))){
                    user.setLastLogin(Instant.now());
                    userRepository.save(user);
                    session.setAttribute("user", user);
                    System.out.println("Login success");
                    return "redirect:/users/home";
                }
            }
        }
        System.out.println("Login fail");
        return "users/login-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPasswordHash(userServices.encrypt(user.getPasswordHash()));
        user.setRegisteredAt(Instant.now());
        userRepository.save(user);
        System.out.println("Registration success");
        return "redirect:/users/login-form";
    }

    @GetMapping("/home")
    public String home(HttpSession session,
                       @RequestParam(name = "page", defaultValue = "1") int page,
                       @RequestParam(name = "size", defaultValue = "10") int size,
                       Model model) {
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("createdAt").descending());
        Page<Post> postPage = postRepository.findAllByPublishedIsTrue(pageRequest);
        model.addAttribute("posts", postPage);
        model.addAttribute("paginatedList", postPage);
        model.addAttribute("user",(User) session.getAttribute("user"));
        return "users/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/users/login-form";
    }

    @GetMapping("/profile/{id}")
    public String profile(HttpSession session,@PathVariable("id") String id, Model model,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "size", defaultValue = "10") int size){
        model.addAttribute("userprofile", userRepository.findById(Long.parseLong(id)).get());
        User userLogin = (User) session.getAttribute("user");
        model.addAttribute("loggedInUserId", userLogin.getId());
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("createdAt").descending());
        Page<Post> postPage = postRepository.findAllByAuthorId(pageRequest, Long.parseLong(id));
        model.addAttribute("posts", postPage);
        model.addAttribute("paginatedList", postPage);
        return "users/profile";
    }

    @PostMapping("/updateProfile")
    public String updateprofile(HttpSession session, Model model,
                                @RequestParam("mobile") String mobile,
                                @RequestParam("email") String email,
                                @RequestParam("intro") String intro,
                                @RequestParam("profile") String profile){
        User user = (User) session.getAttribute("user");
        user.setMobile(mobile);
        user.setEmail(email);
        user.setIntro(intro);
        user.setProfile(profile);
        userRepository.save(user);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return "redirect:/users/profile/"+user.getId();
    }
}
