package com.taotao.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.info("进入全局异常处理器。。。。。");
        logger.debug("其中handler类型为:" + o.getClass());
        e.printStackTrace();
        logger.error("我捕捉到异常：" , e);
        //短信、邮件等通知到开发相关人员
        ModelAndView modelAndView = new ModelAndView("error/exception");
        modelAndView.addObject("msg","您的电脑可能中毒了");
        return modelAndView;
    }
}
