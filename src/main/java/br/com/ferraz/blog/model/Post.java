package br.com.ferraz.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Entity
@Table(name = "POSTS")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subtitle;

    private String body;

    private String slug;

    @Column(columnDefinition = "text")
    private String image;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (slug == null || slug.isBlank()) {
            slug = generateSlug(title);
        }
    }

    @PreUpdate
    public void preUpdate() {
        // regenerate slug if title changed or slug empty
        if (title != null) {
            String newSlug = generateSlug(title);
            if (slug == null || !slug.equals(newSlug)) {
                slug = newSlug;
            }
        }
    }

    private String generateSlug(String input) {
        if (input == null) return null;
        String nowhitespace = input.trim().toLowerCase();
        // remove accents
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // replace non-alphanumeric with hyphens
        String slugified = normalized.replaceAll("[^a-z0-9]+", "-");
        // remove leading/trailing hyphens
        slugified = slugified.replaceAll("(^-+|-+$)", "");
        // collapse multiple hyphens
        slugified = slugified.replaceAll("-+", "-");
        return slugified;
    }

}
