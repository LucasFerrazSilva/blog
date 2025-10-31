package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.dto.post.PostDTO;
import br.com.ferraz.blog.mapper.PostMapper;
import br.com.ferraz.blog.model.Post;
import br.com.ferraz.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {

    private final PostService service;
    private final PostMapper mapper;

    @GetMapping("")
    public ModelAndView home() {
        List<Post> list = service.listRecent();
        List<PostDTO> posts = mapper.toPostDTOList(list);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/ler/{category}/{slug}")
    public ModelAndView readPost(@PathVariable("category") String category,
                                 @PathVariable("slug") String slug) {
        Post post = service.findBySlug(slug);
        ModelAndView modelAndView = new ModelAndView("posts/read");
        modelAndView.addObject("post", mapper.toDTO(post));
        return modelAndView;
    }

    @GetMapping("/categoria/{name}")
    public ModelAndView postsByCategory(@PathVariable("name") String name) {
        List<Post> list = service.listByCategoryName(name);
        List<PostDTO> posts = mapper.toPostDTOList(list);
        ModelAndView modelAndView = new ModelAndView("posts/category");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("categoryName", name);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login.html");
    }

    // search endpoint
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "q", required = false) String q,
                               @RequestParam(value = "page", defaultValue = "0") int page) {
        int size = 1;
        Page<Post> results = service.search(q == null ? "" : q, page, size);
        List<PostDTO> posts = results.stream().map(mapper::toDTO).collect(Collectors.toList());
        ModelAndView mv = new ModelAndView("search/results");
        mv.addObject("posts", posts);
        mv.addObject("totalPages", results.getTotalPages());
        mv.addObject("currentPage", page);
        mv.addObject("q", q);
        return mv;
    }

}
