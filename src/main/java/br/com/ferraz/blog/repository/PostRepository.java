package br.com.ferraz.blog.repository;

import br.com.ferraz.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
