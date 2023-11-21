package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.repositories.PostRepository;

public class PostServices {
    private PostRepository postRepository;

    public PostServices(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> findAllByPublishedIsTrue(Pageable pageable) {
        return postRepository.findAllByPublishedIsTrue(pageable);
    }

}
