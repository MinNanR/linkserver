package site.minnan.linkserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.minnan.linkserver.entites.UserInformation;

@Mapper
public interface UserMapper extends BaseMapper<UserInformation> {
}
