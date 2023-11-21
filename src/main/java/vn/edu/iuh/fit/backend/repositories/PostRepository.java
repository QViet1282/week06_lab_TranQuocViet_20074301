package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.backend.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByPublishedIsTrue(Pageable pageable);
    Page<Post> findAllByAuthorId(Pageable pageable, Long id);
}