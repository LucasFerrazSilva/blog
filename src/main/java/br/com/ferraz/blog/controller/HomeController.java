package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.dto.post.PostDTO;
import br.com.ferraz.blog.mapper.PostMapper;
import br.com.ferraz.blog.model.Post;
import br.com.ferraz.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @GetMapping("/ler/{category}/{postId}")
    public ModelAndView readPost(@PathVariable("category") String category,
                                 @PathVariable("postId") Long postId) {
        Post post = service.findById(postId);
        ModelAndView modelAndView = new ModelAndView("posts/read");
        modelAndView.addObject("post", mapper.toDTO(post));
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login.html");
    }

}
