package br.com.ferraz.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String body;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private LocalDateTime createdAt;

}
