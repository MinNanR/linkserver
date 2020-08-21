package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.minnan.linkserver.entites.DTO.AddToolsDTO;
import site.minnan.linkserver.entites.DTO.DeleteToolsDTO;
import site.minnan.linkserver.entites.DTO.DownloadToolsDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.ToolsVO;
import site.minnan.linkserver.service.ToolsService;
import site.minnan.linkserver.utils.ResponseCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    public ResponseEntity<List<ToolsVO>> getToolsList() {
        List<ToolsVO> toolsList = toolsService.getToolsList();
        ResponseEntity<List<ToolsVO>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        responseEntity.setData(toolsList);
        return responseEntity;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/deleteTools")
    public ResponseEntity<?> deleteTools(@RequestBody DeleteToolsDTO dto) {
        return toolsService.deleteTools(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("api/downloadTools")
    public void downloadTools(DownloadToolsDTO dto, HttpServletResponse response) throws UnsupportedEncodingException {
        toolsService.downloadTools(dto, response);
    }
}
