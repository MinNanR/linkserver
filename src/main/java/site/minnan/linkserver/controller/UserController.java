package site.minnan.linkserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DO.UserInformation;
import site.minnan.linkserver.entites.DTO.AddUserDTO;
import site.minnan.linkserver.entites.DTO.DeleteUserDTO;
import site.minnan.linkserver.entites.DTO.UpdateUserDTO;
import site.minnan.linkserver.entites.DTO.ValidateUserDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.LoginVO;
import site.minnan.linkserver.entites.VO.UserInformationVO;
import site.minnan.linkserver.entites.jwt.JwtRequest;
import site.minnan.linkserver.entites.jwt.JwtUser;
import site.minnan.linkserver.service.UserService;
import site.minnan.linkserver.utils.JwtUtil;
import site.minnan.linkserver.utils.ResponseCode;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @OperateType("登录")
    @PostMapping("${jwt.route.authentication.path}")
    public ResponseEntity<LoginVO> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        log.info(authenticationRequest.toString());
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new Exception("用户名或密码错误", e);
        }
        UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        ResponseEntity<LoginVO> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "登录成功");
        LoginVO vo = new LoginVO();
        vo.setJwtToken(token);
        ArrayList<Object> authoritiesStringList =
                userDetails.getAuthorities().stream().collect(ArrayList::new, (list, au) -> list.add(au.getAuthority()), ArrayList::addAll);
        vo.setRedirectUrl(authoritiesStringList.contains("ADMIN") ? "/manager" : "/link");
        responseEntity.setData(vo);
        return responseEntity;
    }

    @OperateType("查询")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("api/getUserInformation")
    public ResponseEntity<UserInformationVO> getUserInformation(UsernamePasswordAuthenticationToken authenticationToken){
        JwtUser jwtUser = (JwtUser) authenticationToken.getPrincipal();
        UserInformation user = userService.getUserByUsername(jwtUser.getUsername());
        UserInformationVO vo = new UserInformationVO();
        vo.setNickName(user.getNickName());
        ResponseEntity<UserInformationVO> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取用户信息成功");
        responseEntity.setData(vo);
        return responseEntity;
    }

    @OperateType("查询")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/getUserList")
    public ResponseEntity<List<UserInformationVO>> getUserInformationList(){
        List<UserInformationVO> userInformationList = userService.getUserInformationList();
        ResponseEntity<List<UserInformationVO>> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "获取成功");
        responseEntity.setData(userInformationList);
        return responseEntity;
    }

    @OperateType("验证")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/validateUser")
    public ResponseEntity<?> validateUser(UsernamePasswordAuthenticationToken authentication, @RequestBody ValidateUserDTO dto){
        dto.setJwtUser((JwtUser) authentication.getPrincipal());
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "");
        responseEntity.setData(userService.validateUser(dto));
        return responseEntity;
    }

    @OperateType("添加")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/addUser")
    public ResponseEntity<?> addUser(@RequestBody AddUserDTO dto){
        return userService.createUser(dto);
    }

    @OperateType("更新")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO dto){
        return userService.updateUser(dto);
    }

    @OperateType("删除")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("manager/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserDTO dto){
        return userService.deleteUser(dto);
    }
}
