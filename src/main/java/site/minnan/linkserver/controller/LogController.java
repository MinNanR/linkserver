package site.minnan.linkserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DTO.GetLogListDTO;
import site.minnan.linkserver.entites.Log;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.LogListVO;
import site.minnan.linkserver.service.LogService;
import site.minnan.linkserver.utils.ResponseCode;

import java.util.List;

@RestController
public class LogController {

    @Autowired
    private LogService logService;


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/getLogList")
    public ResponseEntity<LogListVO> getLogList(@RequestBody GetLogListDTO dto){
        return logService.getLogList(dto);
    }
}
