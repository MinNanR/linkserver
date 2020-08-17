package site.minnan.linkserver.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.minnan.linkserver.entites.DO.LinkInformation;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.mapper.LinkInformationMapper;
import site.minnan.linkserver.service.LinkService;
import site.minnan.linkserver.utils.ResponseCode;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkInformationMapper linkInformationMapper;


    @Override
    public List<LinkInformation> getAllLinkList() {
        return linkInformationMapper.selectList(null);
    }

    @Override
    public ResponseEntity<?> addLink(LinkInformation linkInformation) {
        if (StringUtils.isEmpty(linkInformation.getLink()) || StringUtils.isEmpty(linkInformation.getName())) {
            return new ResponseEntity<>(ResponseCode.CODE_FAIL, "缺少必要参数");
        }
        int i = linkInformationMapper.insert(linkInformation);
        if (i > 0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "插入成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，插入失败");
    }
}
