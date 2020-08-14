package site.minnan.linkserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.utils.ResponseCode;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<?> handleAuthenticationException(HttpServletRequest request,Exception ex){
        ResponseEntity<?> responseEntity = new ResponseEntity<>();
        log.error("fail to login {}", ex.getMessage());
        responseEntity.setCode(ResponseCode.CODE_INVALID_USER);
        responseEntity.setMessage(ex.getMessage());
        return responseEntity;
    }
}
