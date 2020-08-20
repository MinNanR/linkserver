package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.linkserver.entites.DO.Tools;
import site.minnan.linkserver.entites.DTO.AddToolsDTO;
import site.minnan.linkserver.entites.DTO.DeleteToolsDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.ToolsVO;
import site.minnan.linkserver.service.ToolsService;
import site.minnan.linkserver.utils.ResponseCode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class ToolsController {

    @Autowired
    private ToolsService toolsService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/addTools")
    public ResponseEntity<?> addTools(AddToolsDTO dto) throws IOException {
        return toolsService.addTools(dto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("api/getToolsList")
    public ResponseEntity<List<ToolsVO>> getToolsList(){
        List<ToolsVO> toolsList = toolsService.getToolsList();
        ResponseEntity<List<ToolsVO>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        responseEntity.setData(toolsList);
        return responseEntity;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping("manager/deleteTools")
    public ResponseEntity<?> deleteTools(@RequestBody DeleteToolsDTO dto){
        return toolsService.deleteTools(dto);
    }
}
