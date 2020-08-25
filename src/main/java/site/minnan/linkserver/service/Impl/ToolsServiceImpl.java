package site.minnan.linkserver.service.Impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.minnan.linkserver.entites.DO.Tools;
import site.minnan.linkserver.entites.DTO.AddToolsDTO;
import site.minnan.linkserver.entites.DTO.DeleteToolsDTO;
import site.minnan.linkserver.entites.DTO.DownloadToolsDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.DownloadToolsVO;
import site.minnan.linkserver.entites.VO.ToolsVO;
import site.minnan.linkserver.mapper.ToolsMapper;
import site.minnan.linkserver.service.ToolsService;
import site.minnan.linkserver.utils.ResponseCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToolsServiceImpl implements ToolsService {

    @Autowired
    private ToolsMapper toolsMapper;

    @Autowired
    private OSS oss;

    @Value("${aliyun.bucketName}")
    private String bucketName;

    @Value("${aliyun.baseUrl}")
    private String baseUrl;

    @Override
    @Transactional
    public ResponseEntity<?> addTools(AddToolsDTO dto) throws IOException {
        MultipartFile file = dto.getFile();
        Tools tools = new Tools();
        //将文件信息存入数据库
        tools.setFileName(dto.getName());
        String[] fileExtensionArray = file.getOriginalFilename().split("\\.");
        String fileExtension = fileExtensionArray[fileExtensionArray.length - 1].toLowerCase();
        tools.setExtension(fileExtension);
        tools.setSize(file.getSize());
        tools.setOssKey(String.format("%s.%s", dto.getName(), fileExtension));
        tools.setUpdateTime(Timestamp.from(Instant.now()));
        int i = toolsMapper.insert(tools);
        if (i > 0) {
            //将文件存入阿里云oss
            InputStream is = file.getInputStream();
            oss.putObject(bucketName, tools.getOssKey(), is);
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "保存文件成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "保存文件失败");
    }

    @Override
    public List<ToolsVO> getToolsList() {
        List<Tools> toolsList = toolsMapper.selectList(null);
        return toolsList.stream().map(tools -> {
            ToolsVO vo = new ToolsVO();
            vo.setId(tools.getId());
            vo.setFileName(tools.getFileName());
            vo.setExtension(tools.getExtension());
            vo.setSize(tools.getSize());
            vo.setUpdateTime(tools.getUpdateTime());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> deleteTools(DeleteToolsDTO dto) {
        QueryWrapper<Tools> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", dto.getId());
        Tools tools = toolsMapper.selectOne(queryWrapper);
        if (tools == null) {
            return new ResponseEntity<>(ResponseCode.CODE_FAIL, "工具不存在");
        }
        int i = toolsMapper.delete(queryWrapper);
        if (i > 0) {
            try {
                oss.deleteObject(bucketName, tools.getOssKey());
                return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "删除成功");
            } catch (OSSException | ClientException e) {
                log.error("删除oss文件失败，key：{}", tools.getOssKey());
                return new ResponseEntity<>(ResponseCode.CODE_FAIL, "删除oss内工具失败");
            }
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，删除失败");
    }

    @Override
    public ResponseEntity<DownloadToolsVO> getDownloadInformation(DownloadToolsDTO dto) {
        QueryWrapper<Tools> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", dto.getId());
        Tools tools = toolsMapper.selectOne(queryWrapper);
        if (tools == null) {
            return new ResponseEntity<>(ResponseCode.CODE_FAIL, "工具不存在");
        }
        DownloadToolsVO vo = new DownloadToolsVO();
        String downloadUrl = String.format("%s/%s", baseUrl, tools.getOssKey());
        String fileName = String.format("%s.%s", tools.getFileName(), tools.getExtension());
        vo.setDownloadUrl(downloadUrl);
        vo.setFileName(fileName);
        ResponseEntity<DownloadToolsVO> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取链接成功");
        responseEntity.setData(vo);
        return responseEntity;
    }


}
