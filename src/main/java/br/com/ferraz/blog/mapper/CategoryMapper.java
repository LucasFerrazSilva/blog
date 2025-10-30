package br.com.ferraz.blog.mapper;

import br.com.ferraz.blog.dto.category.CategoryDTO;
import br.com.ferraz.blog.dto.category.NewCategoryDTO;
import br.com.ferraz.blog.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(NewCategoryDTO dto);

    CategoryDTO toCategoryDTO(Category category);

    List<CategoryDTO> toCategoryDTOList(List<Category> categories);
}
