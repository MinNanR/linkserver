package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.DTO.GetLogListDTO;
import site.minnan.linkserver.entites.Log;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.LogListVO;

public interface LogService {

    void addLog(Log log);

    ResponseEntity<LogListVO> getLogList(GetLogListDTO dto);
}
