package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.LinkInformation;

import java.util.List;

public interface LinkService {

    List<LinkInformation> getAllLinks();
}
