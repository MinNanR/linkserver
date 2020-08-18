package site.minnan.linkserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.linkserver.entites.DO.LinkInformation;
import site.minnan.linkserver.entites.DTO.AddLinkDTO;
import site.minnan.linkserver.entites.DTO.DeleteLinkDTO;
import site.minnan.linkserver.entites.DTO.UpdateLinkDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.mapper.LinkInformationMapper;
import site.minnan.linkserver.service.LinkService;
import site.minnan.linkserver.utils.ResponseCode;

import java.sql.Timestamp;
import java.time.Instant;
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
    public ResponseEntity<?> addLink(AddLinkDTO dto) {
        LinkInformation link = new LinkInformation();
        link.setName(dto.getName());
        link.setLink(dto.getLink());
        link.setCreateTime(Timestamp.from(Instant.now()));
        int i = linkInformationMapper.insert(link);
        if (i > 0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "插入成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，插入失败");
    }

    @Override
    public ResponseEntity<?> deleteLink(DeleteLinkDTO dto) {
        QueryWrapper<LinkInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", dto.getId());
        int i = linkInformationMapper.delete(queryWrapper);
        if (i>0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "删除成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，删除失败");
    }

    @Override
    public ResponseEntity<?> updateLink(UpdateLinkDTO dto) {
        LinkInformation updateLink = new LinkInformation();
        updateLink.setName(dto.getName());
        updateLink.setLink(dto.getLink());
        updateLink.setCreateTime(Timestamp.from(Instant.now()));
        int i = linkInformationMapper.updateById(updateLink);
        if (i>0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "更新成功");
        }else{
            return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，更新失败");
        }

    }
}
