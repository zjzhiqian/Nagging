package com.hzq.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzq.common.base.BaseController;
@Controller
@RequestMapping("test")
public class UserController extends BaseController{
	private static final String SUCCESS="test/success";
	@RequestMapping("add")
	public String addUser() {
//		User t=new User(-1,"tom",new Date(),1234);
//		BaseSave(t);
		return SUCCESS;
	}
}
