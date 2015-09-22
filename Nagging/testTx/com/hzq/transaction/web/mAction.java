package com.hzq.transaction.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzq.transaction.service.SecondService;
import com.hzq.transaction.service.TransActionService;

/**
 * @author hzq
 *
 *         2015年8月22日 下午5:42:43
 */
@Controller
public class mAction {
	@Autowired
	private TransActionService transActionService;
	@Autowired
	private SecondService secondService;

	@RequestMapping("reduce/{id}")
	public String testReduce(@PathVariable("id") Integer id) {
		transActionService.reduce(id);
		return "test/success";
	}

	@RequestMapping("reducebatch/{id}")
	public String reducebatch(@PathVariable("id") Integer id) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(1);
		list.add(1);
		secondService.batchReduce(list);
		return "test/success";
	}
}
