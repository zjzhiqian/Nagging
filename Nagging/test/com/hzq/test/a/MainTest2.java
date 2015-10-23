/**
 * @(#)MainTest2.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月22日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.hzq.system.entity.SysUser;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class MainTest2 {
	
	public static void main(String[] args) throws IOException {
		String content="concwqdqwdqwd";
		FileUtils.copyInputStreamToFile(IOUtils.toInputStream(content),new File("E:\\post\\"+"3"+".html"));
	}

}

