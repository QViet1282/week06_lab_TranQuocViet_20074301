package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.repositories.PostRepository;

public class PostServices {
    private PostRepository postRepository;

    public PostServices(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
