package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;

public interface IntroductionService {
    ResponseEntity<?> updateIntroduction(UpdateIntroductionDTO dto);

    IntroductionVO getIntroduction();
}
