package com.hzq.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzq.common.entity.Json;
import com.hzq.common.util.AjaxUtil;

/**
 * @author hzq
 *
 *         2015年9月5日 下午3:31:58
 */
@ControllerAdvice
public class CustomExceptionHandler {
	
	/**
	 * 没有权限时的异常处理
	 * @param ex
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月5日
	 */
	@ExceptionHandler({ AuthorizationException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ModelAndView handleAuthorizationException(HttpServletRequest req,HttpServletResponse res,Exception ex) {
		if(AjaxUtil.isAjaxRequest(req)){
			doForAjax("无权限操作",res,200);
			return null;
		}	
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ex);
		mv.setViewName("error/unauthorized");
		return mv;
	}


	


	/**
	 * 解决数据绑定异常
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ModelAndView handleBindException(Exception ex) {
		// TODO 数据绑定异常的跳转
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ex);
		mv.setViewName("unauthorized");
		return mv;
	}

	@ExceptionHandler({Exception.class })
	public ModelAndView HandleException(HttpServletRequest req,HttpServletResponse res,Exception ex) {
		if(AjaxUtil.isAjaxRequest(req)){
			doForAjax("系统错误", res,200);
			return null;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ex);
		mv.setViewName("error/exception");
		return mv;
	}
	
	/**
	 * ajax请求时的错误处理
	 * @param msg  ajax的提示信息
	 * @param res HttpResponse
	 * @param status 返回的状态码
	 * @author huangzhiqian
	 * @date 2015年10月5日
	 */
	private void doForAjax(String msg, HttpServletResponse res,Integer status) {
		res.setContentType("application/json;charset=UTF-8");
		if(status!=null){
			res.setStatus(status);
		}
		PrintWriter out;
		try {
			out = res.getWriter();
			ObjectMapper mapper=new ObjectMapper();
			Json json=new Json(false,msg);
			String ajaxRs=mapper.writeValueAsString(json);
			out.write(ajaxRs);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
