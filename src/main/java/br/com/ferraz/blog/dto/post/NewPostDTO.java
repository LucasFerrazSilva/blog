package br.com.ferraz.blog.dto.post;

import br.com.ferraz.blog.dto.category.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewPostDTO(
        @NotBlank
        String title,
        @NotBlank
        String body,
        @NotNull
        Long categoryId
) {
}
