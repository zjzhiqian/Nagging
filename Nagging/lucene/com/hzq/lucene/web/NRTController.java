/**
 * @(#)NRTController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月11日 huangzhiqian 创建版本
 */
package com.hzq.lucene.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * 近实时测试
 * @author huangzhiqian
 */
@RequestMapping("lucene")
@Controller
public class NRTController {
	@RequestMapping("NRT")
	public String ShowNRTPage(){
		return "lucene/nrt";
	}
}

