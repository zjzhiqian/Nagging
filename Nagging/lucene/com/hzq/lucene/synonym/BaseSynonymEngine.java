package com.hzq.lucene.synonym;

import java.util.HashMap;
/**
 * 一个同义词的实现类,存储了同义词
 * @author huangzhiqian
 *
 */
public class BaseSynonymEngine implements SynonymEngine {
	//TODO  硬编码,同义词应该从数据库 或者从文件读取
	private static HashMap<String, String[]> map = new HashMap<String, String[]>();
	{	
		//这意味这 查询享受这个词,可以查询到 带可以的
		map.put("享受",new String[]{"可以"});
//		map.put("可以",new String[]{"享受"});
	}

	public String[] getSynonyms(String s) {
		return map.get(s);
	}
}
