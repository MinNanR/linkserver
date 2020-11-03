package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DTO.DeleteToolsDTO;
import site.minnan.linkserver.entites.DTO.DownloadToolsDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.DownloadToolsVO;
import site.minnan.linkserver.entites.VO.ToolsVO;
import site.minnan.linkserver.service.ToolsService;
import site.minnan.linkserver.utils.ResponseCode;

import java.util.List;

@RestController
public class ToolsController {

    @Autowired
    private ToolsService toolsService;


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("api/getToolsList")
    public ResponseEntity<List<ToolsVO>> getToolsList() {
        List<ToolsVO> toolsList = toolsService.getToolsList();
        ResponseEntity<List<ToolsVO>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        responseEntity.setData(toolsList);
        return responseEntity;
    }

    @OperateType("删除")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/deleteTools")
    public ResponseEntity<?> deleteTools(@RequestBody DeleteToolsDTO dto) {
        return toolsService.deleteTools(dto);
    }

    @OperateType("下载")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("api/downloadTools")
    public ResponseEntity<DownloadToolsVO> downloadTools(@RequestBody DownloadToolsDTO dto) {
        return toolsService.getDownloadInformation(dto);
    }
}
