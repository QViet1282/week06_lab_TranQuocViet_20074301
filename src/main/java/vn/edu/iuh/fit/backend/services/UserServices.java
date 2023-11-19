package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.encode.AES;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    private final String keyValue = "1234567890123456";
    private AES aes;
//    public UserServices(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }


    public String encrypt(String plainText){
        byte[] key = keyValue.getBytes();
        aes = new AES(key);
        byte[] kq = aes.ECB_encrypt(plainText.getBytes());
        String reuslt = aes.bytesToHex(kq);
        return reuslt;
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
