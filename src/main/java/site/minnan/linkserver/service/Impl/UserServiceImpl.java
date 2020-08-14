package site.minnan.linkserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.linkserver.entites.SignupDO;
import site.minnan.linkserver.entites.UserInformation;
import site.minnan.linkserver.entites.jwt.JwtUser;
import site.minnan.linkserver.exception.CodeNotValidatedException;
import site.minnan.linkserver.exception.UserExistException;
import site.minnan.linkserver.mapper.UserMapper;
import site.minnan.linkserver.service.UserService;
import site.minnan.linkserver.utils.RedisUtil;

import java.time.Duration;

@Service("CustomUserService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInformation user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), true);
    }

    @Override
    public UserInformation createUser(SignupDO signupInformation) throws CodeNotValidatedException, UserExistException {
        String code = signupInformation.getCode();
        String codeInRedis = (String) redisUtil.getValue("code");
        if (!codeInRedis.equals(code)) {
            throw new CodeNotValidatedException("邀请码错误");
        }
        String username = signupInformation.getUsername();
        UserInformation userInDB = getUserByUsername(username);
        if (userInDB != null) {
            throw new UserExistException("用户名已存在");
        }
        UserInformation user = new UserInformation();
        String encodedPassword = passwordEncoder.encode(signupInformation.getPassword());
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setNickName(signupInformation.getNickName());
        userMapper.insert(user);
        return user;
    }


    private UserInformation getUserByUsername(String username) {
        UserInformation userInformationInRedis = (UserInformation) redisUtil.getValue("user::" + username);
        if (userInformationInRedis != null) {
            return userInformationInRedis;
        }
        QueryWrapper<UserInformation> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        UserInformation userInformationInDB = userMapper.selectOne(userQueryWrapper);

        if (userInformationInDB != null) {
            redisUtil.valueSet("user::" + username, userInformationInDB, Duration.ofDays(7));
        }
        return userInformationInDB;
    }

}