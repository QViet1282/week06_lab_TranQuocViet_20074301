package vn.edu.iuh.fit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
import vn.edu.iuh.fit.backend.services.UserServices;

import java.time.Instant;
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
    @Bean
    CommandLineRunner initData() {
        return args -> {
            Random rnd = new Random();
            for (int i = 1; i < 100; i++) {
                User user = new User(
                        Long.valueOf(i),
                        "Viet" + i,
                        "Quoc" + i,
                        "Tran" + i,
                        "00000000" + i,
                        "quocviet" + i +"@gmail.com",
                        userServices.encrypt("ABCD0000" + i),
                        Instant.now(),
                        Instant.now(),
                        "intro_" + i,
                        "profile_" + i
                );
                userRepository.save(user);
            }
        };
    };
};
