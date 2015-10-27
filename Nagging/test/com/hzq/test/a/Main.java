/**
 * @(#)Main.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月4日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class Main {
	public static void main(String[] args) throws IOException {
//		String salt=SaltGenerator.getSalt(16);
//		SimpleHash hash = new SimpleHash("SHA-1", "123456", salt, 1024);
//		String psw=hash.toHex();
//		System.out.println(psw);
//		System.out.println(salt);
//		
//		System.out.println(new BigDecimal("0.00").setScale(0).toPlainString());
//		
//		File f=new File("E:\\zmenu");
//		File[] files=f.listFiles();
//		StringBuffer buffer=new StringBuffer();
//		for(int i=0;i<files.length;i++){
//			
//			String fileName=files[i].getName();
//			String name=fileName.substring(0,fileName.lastIndexOf("."));
//			buffer.append("\n");
//			buffer.append(".icon-").append(name).append("{").append("\n").append("background:url('icons/").append(fileName).append("') no-repeat center center;").append("\n").append("}").append("\n");	
//		}
		
		//System.out.println(buffer.toString());
		
		//FileUtils.writeStringToFile(new File("E:\\icon.css"), buffer.toString());
		String tmp=FileUtils.readFileToString(new File("E:\\icon.css"));
		String[] rs=tmp.split("}");
		List<Map<String,String>> tmpList=new ArrayList<Map<String,String>>();
		for(int i=0;i<rs.length;i++){
			if("".equals(rs[i].trim())){
				continue;
			}
			int index=rs[i].indexOf("{");
			String icon=rs[i].trim().substring(1,index-2);
			int index_url1=rs[i].indexOf("(");
			int index_url2=rs[i].indexOf(")");
			String tmpurl=rs[i].substring(index_url1+2,index_url2);
			String url=tmpurl.replaceAll("'", "");
			Map<String,String> map=new HashMap<String,String>();
			map.put("icon",icon);
			map.put("url",url);
			tmpList.add(map);
		}
		List<Map<String,String>> Listmap=new ArrayList<Map<String,String>>();
		Listmap.addAll(tmpList);
	//	System.out.println(Listmap);
		ObjectMapper mapper=new ObjectMapper();
		String s=mapper.writeValueAsString(Listmap);
		System.out.println(s);
		
	}
	
	
	
	
	@Test
	public void doTest(){
//		LinkedList<Integer>  a=new LinkedList<Integer>();
//		a.add(1);
//		a.add(2);
////		System.out.println(a.peek());
////		System.out.println(a.element());
////		System.out.println(a.getFirst());
//		System.out.println(a);
//		
////		System.out.println(a.poll());
//		System.out.println(a);
////		System.out.println(a.removeFirst());
////		System.out.println(a.remove());
//		
//		System.out.println(a.pop());
//		System.out.println();
	}
	
	
	
	
	
	
	
	
}

