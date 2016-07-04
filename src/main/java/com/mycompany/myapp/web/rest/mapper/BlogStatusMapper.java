package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.BlogStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BlogStatus and its DTO BlogStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, })
public interface BlogStatusMapper {

    @Mapping(source = "created_by.id", target = "created_byId")
    @Mapping(source = "created_by.login", target = "created_byLogin")
    @Mapping(source = "last_chng_by.id", target = "last_chng_byId")
    @Mapping(source = "last_chng_by.login", target = "last_chng_byLogin")
    BlogStatusDTO blogStatusToBlogStatusDTO(BlogStatus blogStatus);

    List<BlogStatusDTO> blogStatusesToBlogStatusDTOs(List<BlogStatus> blogStatuses);

    @Mapping(source = "created_byId", target = "created_by")
    @Mapping(source = "last_chng_byId", target = "last_chng_by")
    BlogStatus blogStatusDTOToBlogStatus(BlogStatusDTO blogStatusDTO);

    List<BlogStatus> blogStatusDTOsToBlogStatuses(List<BlogStatusDTO> blogStatusDTOs);
}
