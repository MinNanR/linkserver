package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.DTO.AddImageDTO;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;

import java.io.IOException;

public interface IntroductionService {

    ResponseEntity<?> updateIntroduction(UpdateIntroductionDTO dto);

    IntroductionVO getIntroduction();

    ResponseEntity<String> addImage(AddImageDTO dto) throws IOException;
}
