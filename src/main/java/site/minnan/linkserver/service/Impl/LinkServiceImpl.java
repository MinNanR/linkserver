package site.minnan.linkserver.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.linkserver.entites.LinkInformation;
import site.minnan.linkserver.mapper.LinkInformationMapper;
import site.minnan.linkserver.service.LinkService;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkInformationMapper linkInformationMapper;


    @Override
    public List<LinkInformation> getAllLinks() {
        return linkInformationMapper.selectList(null);
    }
}
