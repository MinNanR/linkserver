package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DO.Image;
import site.minnan.linkserver.entites.DTO.DeleteImageDTO;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;
import site.minnan.linkserver.service.IntroductionService;
import site.minnan.linkserver.utils.ResponseCode;

import javax.validation.Valid;
import java.util.List;

@RestController
public class IntroductionController {

    @Autowired
    private IntroductionService introductionService;

    @OperateType("更新")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/updateIntroduction")
    public ResponseEntity<?> updateIntroduction(@RequestBody UpdateIntroductionDTO dto) {
        return introductionService.updateIntroduction(dto);
    }

    @OperateType("查询")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("api/getIntroduction")
    public ResponseEntity<IntroductionVO> getIntroduction() {
        IntroductionVO vo = introductionService.getIntroduction();
        ResponseEntity<IntroductionVO> response = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        response.setData(vo);
        return response;
    }

    @OperateType("查询")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/getImageList")
    public ResponseEntity<List<Image>> getImageList(){
        List<Image> imageList = introductionService.getImageList();
        ResponseEntity<List<Image>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        responseEntity.setData(imageList);
        return responseEntity;
    }

    @OperateType("删除")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/deleteImage")
    public ResponseEntity<?> deleteImage(@RequestBody @Valid DeleteImageDTO dto){
        return introductionService.deleteImage(dto);
    }
}
