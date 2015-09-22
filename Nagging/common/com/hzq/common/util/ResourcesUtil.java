

package com.hzq.common.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
/**
 * 操作配置文件工具类
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
	/**
	 * 格式化
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
