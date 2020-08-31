package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DO.LinkInformation;
import site.minnan.linkserver.entites.DTO.AddLinkDTO;
import site.minnan.linkserver.entites.DTO.DeleteLinkDTO;
import site.minnan.linkserver.entites.DTO.UpdateLinkDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.service.LinkService;
import site.minnan.linkserver.utils.ResponseCode;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class LinkController {

    @Autowired
    LinkService linkService;

    @OperateType("查询")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("api/getAllLinkList")
    public ResponseEntity<List<LinkInformation>> getAllLinks() {
        List<LinkInformation> allLinkList = linkService.getAllLinkList();
        ResponseEntity<List<LinkInformation>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "查询成功");
        responseEntity.setData(allLinkList);
        return responseEntity;
    }

    @OperateType("添加")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/addLink")
    public ResponseEntity<?> addLink(@RequestBody @Valid AddLinkDTO dto) {
        return linkService.addLink(dto);
    }

    @OperateType("删除")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/deleteLink")
    public ResponseEntity<?> deleteLink(@RequestBody @Valid DeleteLinkDTO dto) {
        return linkService.deleteLink(dto);
    }

    @OperateType("更新")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("manager/updateLink")
    public ResponseEntity<?> updateLink(@RequestBody @Valid UpdateLinkDTO dto){
        return linkService.updateLink(dto);
    }
}
