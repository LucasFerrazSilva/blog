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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    private RequestPostProcessor testUser() {
        return user("test").roles("USER");
    }

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        categoryRepository.deleteAll();

        category = new Category();
        category.setName("Tecnologia");
        categoryRepository.save(category);
    }

    @Test
    @DisplayName("POST /posts deve criar um novo post e redirecionar para /posts")
    void shouldCreatePost() throws Exception {
        mockMvc.perform(post("/posts")
                        .with(testUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Primeiro Post")
                        .param("body", "Conteúdo de teste")
                        .param("categoryId", String.valueOf(category.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("Primeiro Post");
    }

    @Test
    @DisplayName("GET /posts deve listar posts existentes")
    void shouldListPosts() throws Exception {
        Post post = new Post();
        post.setTitle("Título Existente");
        post.setBody("Conteúdo Existente");
        post.setCategory(category);
        postRepository.save(post);

        mockMvc.perform(get("/posts")
                        .with(testUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/list"))
                .andExpect(content().string(containsString("Título Existente")));
    }

    @Test
    @DisplayName("GET /posts/form deve exibir o formulário de criação")
    void shouldDisplayFormForNewPost() throws Exception {
        mockMvc.perform(get("/posts/form")
                        .with(testUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/form"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    @DisplayName("GET /posts/form/{id} deve exibir o formulário de edição")
    void shouldDisplayFormForEdit() throws Exception {
        Post post = new Post();
        post.setTitle("Post Editável");
        post.setBody("Conteúdo");
        post.setCategory(category);
        postRepository.save(post);

        mockMvc.perform(get("/posts/form/" + post.getId())
                        .with(testUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/form"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @DisplayName("PUT /posts/{id} deve atualizar um post existente")
    void shouldUpdatePost() throws Exception {
        Post post = new Post();
        post.setTitle("Antigo");
        post.setBody("Conteúdo antigo");
        post.setCategory(category);
        postRepository.save(post);

        mockMvc.perform(put("/posts/" + post.getId())
                        .with(testUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Novo Título")
                        .param("body", "Conteúdo Atualizado")
                        .param("categoryId", String.valueOf(category.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        Post updated = postRepository.findById(post.getId()).orElseThrow();
        assertThat(updated.getTitle()).isEqualTo("Novo Título");
        assertThat(updated.getBody()).isEqualTo("Conteúdo Atualizado");
    }

    @Test
    @DisplayName("GET /posts/{id} deve exibir os detalhes de um post")
    void shouldDisplayPostDetails() throws Exception {
        Post post = new Post();
        post.setTitle("Detalhe Teste");
        post.setBody("Conteúdo Teste");
        post.setCategory(category);
        postRepository.save(post);

        mockMvc.perform(get("/posts/" + post.getId())
                        .with(testUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/read"))
                .andExpect(model().attributeExists("post"))
                .andExpect(content().string(containsString("Detalhe Teste")));
    }

    @Test
    @DisplayName("DELETE /posts/{id} deve remover um post existente")
    void shouldDeletePost() throws Exception {
        Post post = new Post();
        post.setTitle("A Deletar");
        post.setBody("Conteúdo");
        post.setCategory(category);
        postRepository.save(post);

        mockMvc.perform(delete("/posts/" + post.getId())
                        .with(testUser())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        assertThat(postRepository.existsById(post.getId())).isFalse();
    }
}
