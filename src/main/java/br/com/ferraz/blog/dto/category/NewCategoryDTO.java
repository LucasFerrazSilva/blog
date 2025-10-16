package br.com.ferraz.blog.dto.category;

import jakarta.validation.constraints.NotBlank;

public record NewCategoryDTO(@NotBlank String name) {}
