package com.hzq.lucene.synonym;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
/**
 * 同义词来源类型为 txt文件
 * @author huangzhiqian
 *
 */
public class TxtSynonymEngine implements SynonymEngine {
	private static HashMap<String, String[]> map = new HashMap<String, String[]>();
	private static List<String> datas=new LinkedList<String>();
	static {	
//		//这意味这 查询享受这个词,可以查询到 带可以的
//		map.put("享受",new String[]{"可以"});
		InputStream  input =TxtSynonymEngine.class.getClassLoader().getResourceAsStream("synonym.txt");
		try {
			String rs=IOUtils.toString(input,"utf-8");
			String[] synonym=rs.split("\\n");
			for(int i=0;i<synonym.length;i++){
				datas.add(synonym[i].trim());
				String[] same=synonym[i].split(",");
				map.put(same[1].trim(), new String[]{same[0].trim()});
				map.put(same[0].trim(), new String[]{same[1].trim()});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getSynonyms(String s) {
		return map.get(s);
	}
	
	
	public static List<String> getAllSynonym(){
		return datas;
	}
	
	
	
}
