package br.com.ferraz.blog.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewPostDTO(
        @NotBlank
        String title,
        String subtitle,
        @NotBlank
        String body,
        String image,
        @NotNull
        Long categoryId
) {
}
