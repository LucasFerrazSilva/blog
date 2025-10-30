package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.dto.category.CategoryDTO;
import br.com.ferraz.blog.mapper.CategoryMapper;
import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @ModelAttribute("menuCategories")
    public List<CategoryDTO> menuCategories() {
        List<Category> categories = categoryService.list();
        return categoryMapper.toCategoryDTOList(categories);
    }
}

