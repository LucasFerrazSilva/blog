package br.com.ferraz.blog.repository;

import br.com.ferraz.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);

    List<Post> findAllByCategoryNameOrderByCreatedAtDesc(String categoryName);

    // search by title or subtitle or body
    Page<Post> findByTitleContainingIgnoreCaseOrSubtitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String title, String subtitle, String body, Pageable pageable);
}
