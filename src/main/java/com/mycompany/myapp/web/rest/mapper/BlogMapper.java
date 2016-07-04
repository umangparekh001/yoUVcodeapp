package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.BlogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, })
public interface BlogMapper {

    @Mapping(source = "category_id.id", target = "category_idId")
    @Mapping(source = "subCategory_id.id", target = "subCategory_idId")
    @Mapping(source = "status_id.id", target = "status_idId")
    @Mapping(source = "created_by.id", target = "created_byId")
    @Mapping(source = "created_by.login", target = "created_byLogin")
    @Mapping(source = "last_chng_by.id", target = "last_chng_byId")
    @Mapping(source = "last_chng_by.login", target = "last_chng_byLogin")
    BlogDTO blogToBlogDTO(Blog blog);

    List<BlogDTO> blogsToBlogDTOs(List<Blog> blogs);

    @Mapping(source = "category_idId", target = "category_id")
    @Mapping(source = "subCategory_idId", target = "subCategory_id")
    @Mapping(source = "status_idId", target = "status_id")
    @Mapping(source = "created_byId", target = "created_by")
    @Mapping(source = "last_chng_byId", target = "last_chng_by")
    Blog blogDTOToBlog(BlogDTO blogDTO);

    List<Blog> blogDTOsToBlogs(List<BlogDTO> blogDTOs);

    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }

    default SubCategory subcategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        SubCategory subcategory = new SubCategory();
        subcategory.setId(id);
        return subcategory;
    }

    default BlogStatus blogStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        BlogStatus blogStatus = new BlogStatus();
        blogStatus.setId(id);
        return blogStatus;
    }
}
