package com.hzq.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 操作配置文件工具类
 * 
 * @author huangzhiqian
 *
 */
public class ResourcesUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public ResourcesUtil() {
	}

	private static String getProperties(String FileName, String key) {
		String retValue = "";
		try {
			ResourceBundle rb = ResourceBundle.getBundle(FileName);
			retValue = (String) rb.getObject(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}

	public static String getValue(String fileName, String key) {
		String value = getProperties(fileName, key);
		return value;
	}

	public static List<String> gekeyList(String fileName) {
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		List<String> reslist = new ArrayList<String>();
		Set<String> keyset = rb.keySet();
		String lkey;
		for (Iterator<String> it = keyset.iterator(); it.hasNext(); reslist.add(lkey))
			lkey = (String) it.next();

		return reslist;
	}

	public static Map<String,String> readProperties(String filename) {
		Map<String,String> pMap = new HashMap<String,String>();
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filename);
			properties.load(new InputStreamReader(inputStream));
			inputStream.close(); // 关闭流
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				if (!entry.getKey().toString().startsWith("#")) {
					pMap.put(((String) entry.getKey()).trim(), ((String) entry.getValue()).trim());
				}
			}
			return pMap;
		} catch (IOException e) {
		}
		return pMap;
	}

	/**
	 * 格式化
	 * 
	 * @param fileName
	 * @param key
	 * @param objs
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月27日
	 */
	public static String getValue(String fileName, String key, Object objs[]) {
		String pattern = getValue(fileName, key);
		String value = MessageFormat.format(pattern, objs);
		return value;
	}

}
