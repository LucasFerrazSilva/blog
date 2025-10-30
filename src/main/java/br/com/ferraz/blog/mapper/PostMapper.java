package br.com.ferraz.blog.mapper;

import br.com.ferraz.blog.dto.post.NewPostDTO;
import br.com.ferraz.blog.dto.post.PostDTO;
import br.com.ferraz.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface PostMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Post toEntity(NewPostDTO dto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category", target = "category")
    PostDTO toDTO(Post post);

    List<PostDTO> toPostDTOList(List<Post> posts);
}
