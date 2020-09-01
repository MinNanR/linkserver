package site.minnan.linkserver.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.Log;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.LoginVO;
import site.minnan.linkserver.entites.jwt.JwtUser;
import site.minnan.linkserver.mapper.LogMapper;
import site.minnan.linkserver.service.LogService;
import site.minnan.linkserver.utils.JwtUtil;
import site.minnan.linkserver.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogService logService;

    @Autowired
    private JwtUtil jwtUtil;

    @Pointcut("execution(public * site.minnan.linkserver.controller..*..*(..))")
    private void controllerLog() {
    }

    @Pointcut("execution(public * site.minnan.linkserver.controller.UserController.createAuthenticationToken(..))")
    private void loginLog() {
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Before(value = "controllerLog()")
//    public void logBeforeController(JoinPoint joinPoint) throws JsonProcessingException {
//        //TODO 日志
//        Object[] args = joinPoint.getArgs();
//        String argsString = objectMapper.writeValueAsString(args);
//        log.info("controller调用{}，参数：{}", joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
//                , argsString);
//    }

    @Around(value = "controllerLog()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();
        String argsString = objectMapper.writeValueAsString(args);
        String methodFullName = proceedingJoinPoint.getTarget().getClass().getName()
                + "." + proceedingJoinPoint.getSignature().getName();
        log.info("controller调用{}，参数：{}", methodFullName, argsString);
        Object retValue = proceedingJoinPoint.proceed();
        time = System.currentTimeMillis() - time;
        String responseString = objectMapper.writeValueAsString(retValue);
        log.info("controller调用{}完成，返回数据:{}，用时{}ms", methodFullName, responseString, time);
        //构建日志对象
        Log logEntity = new Log();
        //获取操作类型
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        OperateType operateType = methodSignature.getMethod().getAnnotation(OperateType.class);
        String operation = operateType.value();
        logEntity.setOperation(operation);
        //
        logEntity.setRequestUri(request.getRequestURI());
        //获取操作者ip
        String ip = WebUtil.getIpAddr(request);
        //获取操作者
        try {
            JwtUser user =(JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logEntity.setUserId(user.getId());
            logEntity.setUsername(user.getUsername());
        } catch (Exception e) {
            logEntity.setUsername("匿名用户");
        }
        logEntity.setRequestContent(argsString);
        logEntity.setResponseContent(responseString);
        logEntity.setIp(ip);
        logEntity.setCostTime(time);
        logService.addLog(logEntity);
        return retValue;
    }


}
