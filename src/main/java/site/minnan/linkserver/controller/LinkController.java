package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.linkserver.entites.LinkInformation;
import site.minnan.linkserver.service.LinkService;

import java.util.List;

@Controller
public class LinkController {

    @Autowired
    LinkService linkService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping("/link/index")
    public ModelAndView userIndex(@AuthenticationPrincipal UserDetails userDetails){
        System.out.println(userDetails);
        ModelAndView mv = new ModelAndView("link/index");
        List<LinkInformation> allLinks = linkService.getAllLinks();
        mv.addObject("linkList", allLinks);
        return mv;
    }

    @RequestMapping("/test")
    public String test(){
        System.out.println(111);
        return "!11";
    }

}
