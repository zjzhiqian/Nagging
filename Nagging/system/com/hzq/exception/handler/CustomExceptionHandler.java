package com.hzq.exception.handler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hzq
 *
 * 2015年9月5日 下午3:31:58 
 */
@ControllerAdvice
public class CustomExceptionHandler {
		
	  @ExceptionHandler({AuthorizationException.class})
	  @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
	  public ModelAndView handleArithmeticException(Exception ex){
		  	ModelAndView mv = new ModelAndView();  
	        mv.addObject("exception", ex);  
	        mv.setViewName("error/unauthorized");  
	        return mv;  
	  }
	  
	  /*@ExceptionHandler({Exception.class})
	  public ModelAndView handleException(Exception ex){
		  	ModelAndView mv = new ModelAndView();  
	        mv.addObject("exception",321);  
	        mv.setViewName("unauthorized");  
	        return mv;  
	  }*/
	  
	  
	  /**
	   * 解决数据绑定异常
	   * @param ex
	   * @return
	   */
	  @ExceptionHandler({BindException.class})
	  @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
	  public ModelAndView handleBindException(Exception ex){
		  	//TODO 数据绑定异常的跳转
		  	ModelAndView mv = new ModelAndView();  
	        mv.addObject("exception", ex);  
	        mv.setViewName("unauthorized");  
	        return mv;  
	  }
}
