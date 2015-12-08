/**
 * @(#)ExcelController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月2日 huangzhiqian 创建版本
 */
package com.hzq.store.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.Utils;
import com.hzq.common.util.SpringContextUtils;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.store.util.ExcelUtils;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("store")
public class ExcelController {
	@Autowired
	TianYaPostService tianYaPostService;
	
	
	
	@RequestMapping("excel")
	public String showDemoPage(){
		return "store/excel";
	}
	
	/**
	 * Excel导出Demo
	 * @param req
	 * @return
	 * @throws IOException
	 * @author huangzhiqian
	 * @date 2015年12月2日
	 */
	@RequestMapping(value="extractExcel",method=RequestMethod.POST)
	public ResponseEntity<byte[]> extractExcel(HttpServletRequest req) throws IOException{
		QueryCondition condition=Utils.parseRequestToCondition(req);
		Grid<TianYaPost> posts=tianYaPostService.getDataGridResult(condition);
		List<TianYaPost> list=posts.getRows();
        return ExcelUtils.Exeport(list,condition);
	}
	
	
	@RequestMapping(value="importExcel",method=RequestMethod.GET)
	public String importExcelPage(){
		return "store/excelDialog";
	}
	
	/**
	 * 导入Excel公用类
	 * @param file
	 * @param req
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="importExcel",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> importExcel(@RequestParam(value="file") MultipartFile file,HttpServletRequest req) throws Exception{
		InputStream in = null;
		try{
			Map<String,Object>  retMap = new HashMap<String, Object>();
			String clazzName = req.getParameter("clazz");
			String meth = req.getParameter("method");
			String tn = req.getParameter("tn");
			String module = req.getParameter("module");
			String pojo = req.getParameter("pojo");
			in = file.getInputStream();
			byte[] byteArray=IOUtils.toByteArray(in);
			List<Map<String, Object>> listMap = ExcelUtils.getExcelData(byteArray, tn);
			if(listMap.size() != 0){
				Object object = SpringContextUtils.getBean(clazzName);
				Class<?> clazz = Class.forName("com.hzq." + module + ".entity." +pojo);
				Method[] methods = object.getClass().getMethods();
				Method method = null;
				for(Method m : methods){
					if(m.getName().equals(meth)){
						method = m;
					}
				}
				//返回值
				List<Object> addBeanList = new ArrayList<Object>();
				Object bean = null;
				for(Map<String,Object> map:listMap){
					bean=clazz.newInstance();
//					BeanUtils.populate(bean, map);
					Utils.populate(bean, map);
					addBeanList.add(bean);
				}
				try{
					retMap = (Map<String, Object>)method.invoke(object, addBeanList);
				}catch(Exception e){
					retMap.put("exception",e.getCause().getMessage());
				}
			}else{
				retMap.put("excelempty", true);
			}
			return retMap;
			
		}finally{
			IOUtils.closeQuietly(in);
		}
	}
	
}

