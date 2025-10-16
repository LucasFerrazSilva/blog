package br.com.ferraz.blog.service;

import br.com.ferraz.blog.dto.category.NewCategoryDTO;
import br.com.ferraz.blog.dto.category.UpdateCategoryDTO;
import br.com.ferraz.blog.mapper.CategoryMapper;
import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public void create(NewCategoryDTO newCategoryDTO) {
        Category category = categoryMapper.toEntity(newCategoryDTO);
        repository.save(category);
    }

    public List<Category> list() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void update(Long id, UpdateCategoryDTO updateCategoryDTO) {
        Category category = repository.findById(id).orElseThrow();
        category.setName(updateCategoryDTO.name());
        repository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = repository.findById(id).orElseThrow();
        repository.delete(category);
    }
}
