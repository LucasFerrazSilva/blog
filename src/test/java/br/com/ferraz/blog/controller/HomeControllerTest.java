package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.model.Post;
import br.com.ferraz.blog.repository.CategoryRepository;
import br.com.ferraz.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        categoryRepository.deleteAll();

        category = new Category();
        category.setName("Tecnologia");
        categoryRepository.save(category);
    }

    @Test
    @DisplayName("GET / deve exibir posts na home")
    void shouldShowHomeWithPosts() throws Exception {
        Post p1 = new Post();
        p1.setTitle("Post A");
        p1.setBody("Conteúdo A");
        p1.setCategory(category);
        postRepository.save(p1);

        Post p2 = new Post();
        p2.setTitle("Post B");
        p2.setBody("Conteúdo B");
        p2.setCategory(category);
        postRepository.save(p2);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(content().string(containsString("Post A")))
                .andExpect(content().string(containsString("Post B")));
    }

    @Test
    @DisplayName("GET /ler/{category}/{slug} deve exibir os detalhes do post por slug")
    void shouldDisplayPostBySlug() throws Exception {
        Post post = new Post();
        post.setTitle("Detalhe Slug");
        post.setBody("Conteúdo Slug");
        post.setCategory(category);
        postRepository.save(post);

        // após save, slug é gerado na entidade
        String slug = post.getSlug();

        mockMvc.perform(get("/ler/" + category.getName() + "/" + slug))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/read"))
                .andExpect(model().attributeExists("post"))
                .andExpect(content().string(containsString("Detalhe Slug")));
    }

    @Test
    @DisplayName("GET /categoria/{name} deve listar posts da categoria")
    void shouldListPostsByCategory() throws Exception {
        Post p1 = new Post();
        p1.setTitle("Cat Post 1");
        p1.setBody("Conteúdo1");
        p1.setCategory(category);
        postRepository.save(p1);

        mockMvc.perform(get("/categoria/" + category.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/category"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("categoryName"))
                .andExpect(content().string(containsString("Cat Post 1")));
    }

    @Test
    @DisplayName("GET /login deve exibir a página de login")
    void shouldShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"));
    }
}

