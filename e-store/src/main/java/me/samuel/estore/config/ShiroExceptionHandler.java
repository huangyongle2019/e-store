package me.samuel.estore.config;


import me.samuel.estore.common.utils.ResponseUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ShiroExceptionHandler.class);

    @ExceptionHandler(org.apache.shiro.authc.AuthenticationException.class)
    @ResponseBody
    public Object unauthenticatedHandler(AuthenticationException e){
        logger.warn(e.getMessage(),e);
        return ResponseUtil.unlogin();
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Object unauthorizedHandler(AuthorizationException e) {
        logger.warn(e.getMessage(), e);
        return ResponseUtil.unauthz();
    }
}
