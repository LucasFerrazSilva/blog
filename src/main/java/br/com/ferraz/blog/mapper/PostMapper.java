package br.com.ferraz.blog.mapper;

import br.com.ferraz.blog.dto.post.NewPostDTO;
import br.com.ferraz.blog.dto.post.PostDTO;
import br.com.ferraz.blog.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CategoryMapper categoryMapper;

    public Post toEntity(NewPostDTO dto) {
        if (dto == null) return null;
        Post post = new Post();
        post.setTitle(dto.title());
        post.setSubtitle(dto.subtitle());
        post.setBody(dto.body());
        post.setImage(dto.image());
        // category will be set by PostService using categoryId
        return post;
    }

    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        var categoryDto = post.getCategory() == null ? null : categoryMapper.toCategoryDTO(post.getCategory());
        Long categoryId = post.getCategory() == null ? null : post.getCategory().getId();
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getSubtitle(),
                post.getBody(),
                post.getImage(),
                categoryId,
                categoryDto,
                post.getSlug(),
                post.getCreatedAt()
        );
    }

    public List<PostDTO> toPostDTOList(List<Post> posts) {
        if (posts == null) return List.of();
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
