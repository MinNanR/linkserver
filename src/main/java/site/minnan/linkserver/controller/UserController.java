package site.minnan.linkserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.LoginVO;
import site.minnan.linkserver.entites.jwt.JwtRequest;
import site.minnan.linkserver.service.UserService;
import site.minnan.linkserver.utils.JwtUtil;
import site.minnan.linkserver.utils.ResponseCode;

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

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("${jwt.route.authentication.path}")
    @ResponseBody
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


}
