package vn.edu.iuh.fit.backend;

import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
import vn.edu.iuh.fit.backend.services.UserServices;

import java.time.Instant;

public class demo {
    public static void main(String[] args) {
        UserServices userServices = new UserServices();
        User user = new User(
                Long.valueOf(5),
                "firstName",
                "middleName",
                "lastName",
                "mobile_",
                "email_",
                userServices.encrypt("ABCD0000"),
                Instant.now(),
                Instant.now(),
                "intro_",
                "profile_"
        );
        userServices.save(user);
    };
};
