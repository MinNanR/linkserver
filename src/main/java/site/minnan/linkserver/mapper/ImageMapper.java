package site.minnan.linkserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.linkserver.entites.DO.Image;
import site.minnan.linkserver.entites.DO.LinkInformation;

@Repository
@Mapper
public interface ImageMapper extends BaseMapper<Image> {
}
