package br.com.ferraz.blog.dto.post;

import br.com.ferraz.blog.dto.category.CategoryDTO;

import java.time.LocalDateTime;

public record PostDTO(
        Long id,
        String title,
        String body,
        Long categoryId,
        CategoryDTO category,
        LocalDateTime createdAt
) {
}
