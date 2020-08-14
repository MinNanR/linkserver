package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.minnan.linkserver.entites.LinkInformation;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.service.LinkService;
import site.minnan.linkserver.utils.ResponseCode;

import java.util.List;

@RestController
@CrossOrigin
public class LinkController {

    @Autowired
    LinkService linkService;

    @PostMapping("api/getAllLinkList")
    public ResponseEntity<List<LinkInformation>> getAllLinks(){
        List<LinkInformation> allLinkList = linkService.getAllLinkList();
        ResponseEntity<List<LinkInformation>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "查询成功");
        responseEntity.setData(allLinkList);
        return responseEntity;
    }

    @PostMapping("manager/addLink")
    public ResponseEntity<?> addLink(@RequestBody LinkInformation linkInformation){
        return linkService.addLink(linkInformation);
    }

    public ResponseEntity<?> deleteLink(@RequestBody LinkInformation linkInformation){
        return null;
    }
}
