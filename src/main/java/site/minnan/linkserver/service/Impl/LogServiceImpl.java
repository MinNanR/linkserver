package site.minnan.linkserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.linkserver.entites.DTO.GetLogListDTO;
import site.minnan.linkserver.entites.Log;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.LogListVO;
import site.minnan.linkserver.mapper.LogMapper;
import site.minnan.linkserver.service.LogService;
import site.minnan.linkserver.utils.ResponseCode;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {
        log.setCreateTime(Timestamp.from(Instant.now()));
        logMapper.insert(log);
    }

    @Override
    public ResponseEntity<LogListVO> getLogList(GetLogListDTO dto) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        if (dto.getOperation() != null) {
            queryWrapper.eq("operation", dto.getOperation());
        }
        queryWrapper.orderByDesc("create_time");
        PageHelper.startPage(dto.getPageIndex(), dto.getPageSize());
        Page<Log> logList = (Page<Log>) logMapper.selectList(queryWrapper);
        LogListVO vo = new LogListVO();
        vo.setList(logList.stream().map(log -> {
            LogListVO.LogVO logVO = new LogListVO.LogVO();
            logVO.setUsername(log.getUsername());
            logVO.setRequestUri(log.getRequestUri());
            logVO.setOperation(log.getOperation());
            logVO.setIp(log.getIp());
            logVO.setCreateTime(log.getCreateTime());
            return logVO;
        }).collect(Collectors.toList()));
        vo.setTotalCount(logList.getTotal());
        ResponseEntity<LogListVO> responseEntity = new ResponseEntity<>();
        responseEntity.setData(vo);
        responseEntity.setCode(ResponseCode.CODE_SUCCESS);
        responseEntity.setMessage("获取日志成功");
        return responseEntity;
    }
}
