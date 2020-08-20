package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;
import site.minnan.linkserver.service.IntroductionService;
import site.minnan.linkserver.utils.ResponseCode;

@RestController
public class IntroductionController {

    @Autowired
    private IntroductionService introductionService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/updateIntroduction")
    public ResponseEntity<?> updateIntroduction(@RequestBody UpdateIntroductionDTO dto){
        return introductionService.updateIntroduction(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("api/getIntroduction")
    public ResponseEntity<IntroductionVO> getIntroduction(){
        IntroductionVO vo = introductionService.getIntroduction();
        ResponseEntity<IntroductionVO> response = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        response.setData(vo);
        return response;
    }
}
