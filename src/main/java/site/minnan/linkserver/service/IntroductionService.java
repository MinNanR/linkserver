package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.DO.Image;
import site.minnan.linkserver.entites.DTO.AddImageDTO;
import site.minnan.linkserver.entites.DTO.DeleteImageDTO;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;
import site.minnan.linkserver.utils.ResponseCode;

import java.io.IOException;
import java.util.List;

public interface IntroductionService {

    ResponseEntity<?> updateIntroduction(UpdateIntroductionDTO dto);

    IntroductionVO getIntroduction();

    ResponseEntity<?> addImage(AddImageDTO dto) throws IOException;

    List<Image> getImageList();

    ResponseEntity<?> deleteImage(DeleteImageDTO dto);
}
