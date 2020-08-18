package site.minnan.linkserver.service;

import site.minnan.linkserver.entites.DO.LinkInformation;
import site.minnan.linkserver.entites.DTO.AddLinkDTO;
import site.minnan.linkserver.entites.DTO.DeleteLinkDTO;
import site.minnan.linkserver.entites.DTO.UpdateLinkDTO;
import site.minnan.linkserver.entites.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

public interface LinkService {

    List<LinkInformation> getAllLinkList();

    ResponseEntity<?> addLink(AddLinkDTO dto);

    ResponseEntity<?> deleteLink(DeleteLinkDTO dto);

    ResponseEntity<?> updateLink(UpdateLinkDTO dto);
}
