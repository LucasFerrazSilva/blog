package br.com.ferraz.blog.service;

import br.com.ferraz.blog.dto.post.NewPostDTO;
import br.com.ferraz.blog.dto.post.UpdatePostDTO;
import br.com.ferraz.blog.mapper.PostMapper;
import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.model.Post;
import br.com.ferraz.blog.repository.CategoryRepository;
import br.com.ferraz.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper mapper;
    private final PostRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Post create(NewPostDTO newPostDTO) {
        Post post = mapper.toEntity(newPostDTO);
        Category category = categoryRepository.findById(newPostDTO.categoryId()).orElseThrow();
        post.setCategory(category);
        return repository.save(post);
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Post> list() {
        return repository.findAll();
    }

    @Transactional
    public Post update(Long id, UpdatePostDTO updatePostDTO) {
        Post post = repository.findById(id).orElseThrow();
        BeanUtils.copyProperties(updatePostDTO, post);
        Category category = categoryRepository.findById(updatePostDTO.categoryId()).orElseThrow();
        post.setCategory(category);
        return repository.save(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = repository.findById(id).orElseThrow();
        repository.delete(post);
    }

}
