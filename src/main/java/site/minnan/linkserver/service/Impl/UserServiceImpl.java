package site.minnan.linkserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.linkserver.entites.DO.UserInformation;
import site.minnan.linkserver.entites.DTO.AddUserDTO;
import site.minnan.linkserver.entites.DTO.DeleteUserDTO;
import site.minnan.linkserver.entites.DTO.UpdateUserDTO;
import site.minnan.linkserver.entites.DTO.ValidateUserDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.UserInformationVO;
import site.minnan.linkserver.entites.jwt.JwtUser;
import site.minnan.linkserver.mapper.UserMapper;
import site.minnan.linkserver.service.UserService;
import site.minnan.linkserver.utils.JwtUtil;
import site.minnan.linkserver.utils.RedisUtil;
import site.minnan.linkserver.utils.ResponseCode;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service("CustomUserService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInformation user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), true);
    }

    @Override
    public ResponseEntity<?> createUser(AddUserDTO dto) {
        QueryWrapper<UserInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", dto.getUsername());
        UserInformation userInDB = userMapper.selectOne(queryWrapper);
        if (userInDB != null) {
            return new ResponseEntity<>(ResponseCode.CODE_FAIL, "用户名已存在");
        }
        UserInformation newUser = new UserInformation();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setNickName(dto.getNickName());
        newUser.setRole("USER");
        int i = userMapper.insert(newUser);
        if (i > 0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "添加成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，添加失败");
    }

    @Override
    public List<UserInformationVO> getUserInformationList() {
        List<UserInformation> userInformationList = userMapper.selectList(null);
        return userInformationList.stream().map(user -> {
            UserInformationVO vo = new UserInformationVO();
            vo.setId(user.getId());
            vo.setUsername(user.getUsername());
            vo.setNickName(user.getNickName());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean validateUser(ValidateUserDTO dto) {
        return passwordEncoder.matches(dto.getPassword(), dto.getJwtUser().getPassword());
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO dto) {
        UserInformation user = new UserInformation();
        user.setId(dto.getId());
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getNickName() != null) {
            user.setNickName(dto.getNickName());
        }
        int i = userMapper.updateById(user);
        if (i>0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "修改成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，修改失败");
    }

    @Override
    public ResponseEntity<?> deleteUser(DeleteUserDTO dto) {
        int i = userMapper.deleteById(dto.getId());
        if (i>0) {
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "删除成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "数据库错误，删除失败");
    }

    @Override
    public UserInformation getUserByUsername(String username) {
        UserInformation userInformation = (UserInformation) redisUtil.getValue("user::" + username);
        if (userInformation != null) {
            return userInformation;
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
