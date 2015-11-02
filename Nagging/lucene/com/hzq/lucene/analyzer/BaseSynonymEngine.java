package com.hzq.lucene.analyzer;

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
		map.put("故事",new String[]{"梦想"});
		map.put("梦想",new String[]{"故事"});
	}

	public String[] getSynonyms(String s) {
		return map.get(s);
	}
}
