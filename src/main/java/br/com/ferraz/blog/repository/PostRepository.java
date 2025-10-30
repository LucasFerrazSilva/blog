package br.com.ferraz.blog.repository;

import br.com.ferraz.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);

    List<Post> findAllByCategoryNameOrderByCreatedAtDesc(String categoryName);
}
