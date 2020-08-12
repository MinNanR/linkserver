package site.minnan.linkserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.linkserver.entites.LinkInformation;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.service.LinkService;
import site.minnan.linkserver.utils.ResponseCode;

import java.util.List;

@RestController
@Slf4j
public class LinkController {

    @Autowired
    LinkService linkService;

    @RequestMapping("/getAllLinks")
    public ResponseEntity<List<LinkInformation>> getAllLinkInformationList(@AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails.toString());
        List<LinkInformation> allLinks = linkService.getAllLinks();
        ResponseEntity<List<LinkInformation>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS,
                "获取链接成功");
        responseEntity.setData(allLinks);
        return responseEntity;
    }

}
