package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.dto.category.CategoryDTO;
import br.com.ferraz.blog.dto.post.NewPostDTO;
import br.com.ferraz.blog.dto.post.PostDTO;
import br.com.ferraz.blog.dto.post.UpdatePostDTO;
import br.com.ferraz.blog.mapper.CategoryMapper;
import br.com.ferraz.blog.mapper.PostMapper;
import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.model.Post;
import br.com.ferraz.blog.service.CategoryService;
import br.com.ferraz.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    private final CategoryService categoryService;
    private final PostMapper mapper;
    private final CategoryMapper categoryMapper;

    private static final String FORM_VIEW = "posts/form";
    private static final String REDIRECT_TO_LIST = "redirect:/posts";

    @GetMapping("/form")
    public ModelAndView form() {
        return buildFormModelAndView(null);
    }

    @GetMapping("/form/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return buildFormModelAndView(id);
    }

    @PostMapping
    public ModelAndView create(@Valid @ModelAttribute("post") NewPostDTO newPostDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(FORM_VIEW);
            modelAndView.addObject("post", newPostDTO);
            return modelAndView;
        }

        service.create(newPostDTO);
        return new ModelAndView(REDIRECT_TO_LIST);
    }

    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable("id") Long id) {
        Post post = service.findById(id);
        PostDTO dto = mapper.toDTO(post);
        ModelAndView modelAndView = new ModelAndView("posts/read");
        modelAndView.addObject("post", dto);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView list() {
        List<Post> list = service.list();
        List<PostDTO> posts = mapper.toPostDTOList(list);
        ModelAndView modelAndView = new ModelAndView("posts/list");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @PutMapping("/{id}")
    public ModelAndView update(@PathVariable("id") Long id, @Valid @ModelAttribute("post") UpdatePostDTO updatePostDTO, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(FORM_VIEW);
            modelAndView.addObject("post", updatePostDTO);
            return modelAndView;
        }

        service.update(id, updatePostDTO);
        return new ModelAndView(REDIRECT_TO_LIST);
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ModelAndView(REDIRECT_TO_LIST);
    }

    private ModelAndView buildFormModelAndView(Long id) {
        boolean isUpdate = id != null;

        ModelAndView modelAndView = new ModelAndView(FORM_VIEW);

        Post post = isUpdate ? service.findById(id) : new Post();
        modelAndView.addObject("post", mapper.toDTO(post));

        List<Category> categories = categoryService.list();
        List<CategoryDTO> categoryDTOList = categoryMapper.toCategoryDTOList(categories);
        modelAndView.addObject("categories", categoryDTOList);

        modelAndView.addObject("formAction", isUpdate ? "/posts/" + id : "/posts");

        modelAndView.addObject("formMethod", isUpdate ? "put" : "post");
        return modelAndView;
    }

}
