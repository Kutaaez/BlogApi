package kz.seppaku.postmanBlog.mapper;

import kz.seppaku.postmanBlog.dto.response.ThreadDto;
import kz.seppaku.postmanBlog.entities.Thread;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {ReviewMapper.class, CategoryMapper.class, LikeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ThreadMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.actualUsername", target = "username")
    @Mapping(source = "commentaries", target = "reviews")
    ThreadDto toDto(Thread thread);

    @Mapping(target = "user", ignore = true)
    @Mapping(source = "reviews", target = "commentaries")
    Thread toEntity(ThreadDto threadDto);
}