package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.DTO.AddToolsDTO;
import site.minnan.linkserver.entites.DTO.DeleteToolsDTO;
import site.minnan.linkserver.entites.DTO.DownloadToolsDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.ToolsVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ToolsService {

    ResponseEntity<?> addTools(AddToolsDTO dto) throws IOException;

    List<ToolsVO> getToolsList();

    ResponseEntity<?> deleteTools(DeleteToolsDTO dto);

    ResponseEntity<?> downloadTools(DownloadToolsDTO dto, HttpServletResponse response) throws UnsupportedEncodingException;
}
