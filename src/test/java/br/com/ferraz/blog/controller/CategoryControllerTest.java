package br.com.ferraz.blog.controller;

import br.com.ferraz.blog.model.Category;
import br.com.ferraz.blog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setup() {
        repository.save(new Category("Tecnologia"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = "ADMIN")
    void shouldListCategories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/list"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = "ADMIN")
    void shouldCreateCategory() throws Exception {
        mockMvc.perform(post("/categories")
                        .with(csrf())
                        .param("name", "Ciência"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = "ADMIN")
    void shouldShowEditForm() throws Exception {
        Category category = repository.findAll().get(0);

        mockMvc.perform(get("/categories/form/" + category.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/form"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("formAction", "/categories/" + category.getId()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = "ADMIN")
    void shouldUpdateCategory() throws Exception {
        Category category = repository.findAll().get(0);

        mockMvc.perform(post("/categories/" + category.getId())
                        .with(csrf())
                        .param("_method", "PUT")
                        .param("name", "Atualizado"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = "ADMIN")
    void shouldDeleteCategory() throws Exception {
        Category category = repository.findAll().get(0);

        mockMvc.perform(post("/categories/" + category.getId())
                        .with(csrf())
                        .param("_method", "DELETE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }
}
