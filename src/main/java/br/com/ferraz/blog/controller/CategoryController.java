package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.dto.category.CategoryDTO;
import br.com.ferraz.blog.dto.category.NewCategoryDTO;
import br.com.ferraz.blog.dto.category.UpdateCategoryDTO;
import br.com.ferraz.blog.mapper.CategoryMapper;
import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ModelAndView list() {
        List<Category> categories = service.list();
        List<CategoryDTO> dtoList = categoryMapper.toCategoryDTOList(categories);
        ModelAndView modelAndView = new ModelAndView("categories/list");
        modelAndView.addObject("categories", dtoList);
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        return buildFormModelAndView(null);
    }

    @GetMapping("/form/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return buildFormModelAndView(id);
    }

    public ModelAndView buildFormModelAndView(Long id) {
        Category category = id != null ? service.findById(id) : new Category();
        ModelAndView modelAndView = new ModelAndView("categories/form");
        modelAndView.addObject("category", category);
        modelAndView.addObject("formAction", id != null ? "/categories/" + id : "/categories");
        modelAndView.addObject("formMethod", id != null ? "put" : "post");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView save(@Valid @ModelAttribute("category") NewCategoryDTO newCategoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("categories/form");
            modelAndView.addObject("category", newCategoryDTO);
            return modelAndView;
        }

        service.create(newCategoryDTO);
        return new ModelAndView("redirect:/categories");
    }

    @PutMapping("/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, @Valid @ModelAttribute("category") UpdateCategoryDTO updateCategoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("categories/form/" + id);
            modelAndView.addObject("category", updateCategoryDTO);
            return modelAndView;
        }

        service.update(id, updateCategoryDTO);
        return new ModelAndView("redirect:/categories");
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ModelAndView("redirect:/categories");
    }
}
