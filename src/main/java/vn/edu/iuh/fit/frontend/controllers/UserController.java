package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
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
}
