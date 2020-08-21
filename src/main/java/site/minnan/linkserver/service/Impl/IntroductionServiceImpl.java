package site.minnan.linkserver.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;
import site.minnan.linkserver.service.IntroductionService;
import site.minnan.linkserver.utils.RedisUtil;
import site.minnan.linkserver.utils.ResponseCode;

@Service
public class IntroductionServiceImpl implements IntroductionService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ResponseEntity<?> updateIntroduction(UpdateIntroductionDTO dto) {
        redisUtil.valueSet("introduction", dto.getContent());
        return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "修改成功");
    }

    @Override
    public IntroductionVO getIntroduction() {
        String introduction = (String) redisUtil.getValue("introduction");
        return new IntroductionVO(StringUtils.isEmpty(introduction) ? "敬请期待" : introduction);
    }
}
