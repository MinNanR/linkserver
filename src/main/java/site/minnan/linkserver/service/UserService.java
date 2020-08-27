package site.minnan.linkserver.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.linkserver.entites.DO.UserInformation;
import site.minnan.linkserver.entites.DTO.AddUserDTO;
import site.minnan.linkserver.entites.DTO.DeleteUserDTO;
import site.minnan.linkserver.entites.DTO.UpdateUserDTO;
import site.minnan.linkserver.entites.DTO.ValidateUserDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.UserInformationVO;

import java.util.List;

public interface UserService extends UserDetailsService {

    ResponseEntity<?> createUser(AddUserDTO addUserDTO);

    List<UserInformationVO> getUserInformationList();

    Boolean validateUser(ValidateUserDTO dto);

    ResponseEntity<?> updateUser(UpdateUserDTO dto);

    ResponseEntity<?> deleteUser(DeleteUserDTO dto);

    UserInformation getUserByUsername(String username);
}
