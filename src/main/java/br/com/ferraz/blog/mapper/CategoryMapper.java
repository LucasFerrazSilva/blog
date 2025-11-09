package br.com.ferraz.blog.mapper;

import br.com.ferraz.blog.dto.category.CategoryDTO;
import br.com.ferraz.blog.dto.category.NewCategoryDTO;
import br.com.ferraz.blog.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toEntity(NewCategoryDTO dto) {
        if (dto == null) return null;
        return new Category(dto.name());
    }

    public CategoryDTO toCategoryDTO(Category category) {
        if (category == null) return null;
        return new CategoryDTO(category.getId(), category.getName());
    }

    public List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
