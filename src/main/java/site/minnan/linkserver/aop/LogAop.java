package site.minnan.linkserver.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import site.minnan.linkserver.annotation.OperateType;
import site.minnan.linkserver.entites.DTO.FileDTO;
import site.minnan.linkserver.entites.Log;
import site.minnan.linkserver.entites.jwt.JwtUser;
import site.minnan.linkserver.service.LogService;
import site.minnan.linkserver.utils.JwtUtil;
import site.minnan.linkserver.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Pointcut("execution(public * site.minnan.linkserver.controller.FileController..*(..))")
    private void fileLog() {
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around(value = "controllerLog() && !fileLog()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();
        List<String> argStringList = Arrays.stream(args).map(Object::toString).collect(Collectors.toList());
        String argsString = objectMapper.writeValueAsString(argStringList);
        String methodFullName = proceedingJoinPoint.getTarget().getClass().getName()
                + "." + proceedingJoinPoint.getSignature().getName();
        log.info("controller调用{}，参数：{}", methodFullName, argsString);
        Object retValue = null;
        try {
            retValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("调用接口异常", throwable);
        }
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
            JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @Around(value = "fileLog()")
    public Object logAroundFileUpload(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();
        FileDTO fileDTO = (FileDTO) args[0];
        MultipartFile file = fileDTO.getMultipartFile();
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("文件名", file.getName());
        argsMap.put("文件大小", file.getSize());
        String argsString = objectMapper.writeValueAsString(argsMap);
        String methodFullName = proceedingJoinPoint.getTarget().getClass().getName()
                + "." + proceedingJoinPoint.getSignature().getName();
        log.info("controller调用{}，参数：{}", methodFullName, argsString);
        Object retValue = null;
        try {
            retValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("调用接口异常", throwable);
            throw throwable;
        }
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
            JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
