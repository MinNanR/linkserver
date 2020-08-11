package site.minnan.linkserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.linkserver.entites.SignupDO;
import site.minnan.linkserver.entites.UserInformation;
import site.minnan.linkserver.entites.jwt.JwtRequest;
import site.minnan.linkserver.entites.jwt.JwtResponse;
import site.minnan.linkserver.entites.jwt.JwtUser;
import site.minnan.linkserver.exception.CodeNotValidatedException;
import site.minnan.linkserver.exception.UserExistException;
import site.minnan.linkserver.service.UserService;
import site.minnan.linkserver.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

@Controller
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

    @RequestMapping("/authentication/loginFail")
    public String loginFail() {
        return "login";
    }


    @PostMapping("${jwt.route.authentication.path}")
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        log.info(authenticationRequest.toString());
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            e.printStackTrace();
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e){
            e.printStackTrace();
        }
        UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/token")
    @ResponseBody
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        return (JwtUser) userService.loadUserByUsername(username);
    }


//    @PostMapping("/user/signup")
//    public ModelAndView signup(SignupDO signupDO) {
//        ModelAndView mv = new ModelAndView();
//        try {
//            UserInformation newUser = userService.createUser(signupDO);
//            mv.setViewName("redirect: /login");
//        } catch (CodeNotValidatedException | UserExistException e) {
//            mv.addObject("errorMessage", e.getMessage());
//            mv.setViewName("signup");
//        }
//        return mv;
//    }

}
