package br.com.ferraz.blog.repository;

import br.com.ferraz.blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
