package site.minnan.linkserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.linkserver.entites.DO.LinkInformation;

@Repository
@Mapper
public interface LinkInformationMapper extends BaseMapper<LinkInformation> {

}
