package site.minnan.linkserver.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.linkserver.entites.SignupDO;
import site.minnan.linkserver.entites.UserInformation;
import site.minnan.linkserver.exception.CodeNotValidatedException;
import site.minnan.linkserver.exception.UserExistException;

public interface UserService extends UserDetailsService {

    UserInformation createUser(SignupDO singupInformation) throws CodeNotValidatedException, UserExistException;
}
