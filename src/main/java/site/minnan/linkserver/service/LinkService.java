package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.LinkInformation;
import site.minnan.linkserver.entites.ResponseEntity;

import java.util.List;

public interface LinkService {

    List<LinkInformation> getAllLinkList();

    ResponseEntity<?> addLink(LinkInformation linkInformation);
}
