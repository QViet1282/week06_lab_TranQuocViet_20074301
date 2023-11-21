package vn.edu.iuh.fit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.PostComment;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
import vn.edu.iuh.fit.backend.services.UserServices;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class LabWeek06Application {

    public static void main(String[] args) {
        SpringApplication.run(LabWeek06Application.class, args);
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private vn.edu.iuh.fit.backend.repositories.PostRepository postRepository;
    @Autowired
    private vn.edu.iuh.fit.backend.repositories.PostCommentRepository postCommentRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            Random rnd = new Random();
            List<User> users = userRepository.findAll();
            List<Post> posts = postRepository.findAll();
            for (int i = 1; i < 100; i++) {
//                User user = new User(
//                        Long.valueOf(i),
//                        "Viet" + i,
//                        "Quoc" + i,
//                        "Tran" + i,
//                        "00000000" + i,
//                        "quocviet" + i +"@gmail.com",
//                        userServices.encrypt("ABCD0000" + i),
//                        Instant.now(),
//                        Instant.now(),
//                        "intro_" + i,
//                        "profile_" + i
//                );
//                userRepository.save(user);

//                User randomUser = users.get(rnd.nextInt(users.size()));
//                Post post = new Post();
//                post.setAuthor(randomUser);
//                post.setTitle("Title " + i);
//                post.setMetaTitle("Meta Title " + i);
//                post.setSummary("Summary " + i);
//                post.setPublished(rnd.nextBoolean());
//                post.setCreatedAt(Instant.now());
//                post.setUpdatedAt(Instant.now());
//                post.setPublishedAt(Instant.now());
//                post.setContent("Content " + i);
//
//                // Lưu bài đăng vào cơ sở dữ liệu
//                postRepository.save(post);

//                User randomUser = users.get(rnd.nextInt(users.size()));
//                Post randomPost = posts.get(rnd.nextInt(posts.size()));
//                PostComment comment = new PostComment();
//                comment.setPost(randomPost);
//                comment.setParent(null);
//                comment.setTitle("Comment " + i);
//                comment.setUser(randomUser);
//                comment.setPublished(rnd.nextBoolean());
//                comment.setCreatedAt(Instant.now());
//                comment.setPublishedAt(Instant.now());
//                comment.setContent("Comment Content" + i);
//
//                // Lưu comment vào cơ sở dữ liệu
//                postCommentRepository.save(comment);
            }
        };
    };
};
