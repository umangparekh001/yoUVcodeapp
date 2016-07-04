package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.BlogTagsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BlogTags and its DTO BlogTagsDTO.
 */
@Mapper(componentModel = "spring", uses = {BlogMapper.class, })
public interface BlogTagsMapper {

    BlogTagsDTO blogTagsToBlogTagsDTO(BlogTags blogTags);

    List<BlogTagsDTO> blogTagsToBlogTagsDTOs(List<BlogTags> blogTags);

    BlogTags blogTagsDTOToBlogTags(BlogTagsDTO blogTagsDTO);

    List<BlogTags> blogTagsDTOsToBlogTags(List<BlogTagsDTO> blogTagsDTOs);

    default Blog blogFromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
