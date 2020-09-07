package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DTO.AddImageDTO;
import site.minnan.linkserver.entites.DTO.AddToolsDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.service.IntroductionService;
import site.minnan.linkserver.service.ToolsService;

import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private IntroductionService introductionService;

    @Autowired
    private ToolsService toolsService;

    @OperateType("文件上传")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/addTools")
    public ResponseEntity<?> addTools(AddToolsDTO dto) throws IOException {
        return toolsService.addTools(dto);
    }

    @OperateType("文件上传")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/addImage")
    public ResponseEntity<String> insertImage(AddImageDTO dto) throws IOException {
        return introductionService.addImage(dto);
    }
}
