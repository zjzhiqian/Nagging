/**
 * @(#)UpdateDBServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年1月4日 huangzhiqian 创建版本
 */
package com.hzq.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.common.util.ResourcesUtil;
//import com.hd.agent.common.util.PropertiesUtils;
//import com.hd.agent.system.model.UpdateDB;
import com.hzq.system.dao.UpdateDBMapper;
import com.hzq.system.entity.UpdateDB;
import com.hzq.system.service.UpdateDBService;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Service
public class UpdateDBServiceImpl implements UpdateDBService{
	@Autowired
	UpdateDBMapper updateDBMapper;
	@Override
	public boolean updateDB() throws Exception {
		int i = updateDBMapper.isHasUpdateDB();
		if(i==0){
			updateDBMapper.createUpdateDB();
		}
		String path = getUpdatePath();
		File file = new File(path);   
        File[] array = file.listFiles();
        List<File> fileList = new ArrayList<File>();
        for (File f : array) {
            fileList.add(f);
        }
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                if(o1.getName().length()>o2.getName().length()){
                    return 1;
                }else{
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        //获取数据库配置信息
		Map<String,String> map = ResourcesUtil.readProperties(getClassPath()+"config.properties");
		String driverClassName = map.get("driverClassName");
		String url = map.get("url");
		String username =  map.get("user");
		String password =  map.get("psw");
		//密码解密
//		password = ConfigTools.decrypt(passwordEncrypt);
		
        for(File fileObject :fileList){
        	String fileName = fileObject.getName();
			String updateSql = path+fileName;
			//判断该文件是否执行过
			UpdateDB updateDB = updateDBMapper.getUpdateDBByName(fileName);
			if(null==updateDB){
                boolean flag = true;
                try {
                    SQLExec sqlExec = new SQLExec();
                    // 设置数据库参数
                    sqlExec.setDriver(driverClassName);
                    sqlExec.setUrl(url);
                    sqlExec.setUserid(username);
                    sqlExec.setPassword(password);
                    sqlExec.setEncoding("UTF-8");
                    sqlExec.setSrc(new File(updateSql));
                    // 要指定这个属性，不然会出错
                    sqlExec.setProject(new Project());
                    //设定分隔符 sql文件中 多条sql语句 以￥分割
                    sqlExec.setDelimiter(";");
                    sqlExec.setKeepformat(true);
                    sqlExec.execute();
                }catch (Exception e){
                    flag = false;
                    e.printStackTrace();//可以日志记录
                }
				if(flag){
                    UpdateDB addUpdateDB = new UpdateDB();
                    addUpdateDB.setName(fileName);
                    updateDBMapper.addUpdateDBLog(addUpdateDB);
                }

			}
        }
		
		return false;
	}
	
	public String getUpdatePath() throws Exception{
		String path = getClass().getClassLoader().getResource("").getPath().replaceAll("%20", " ");
		int pos = -1;
		if (path.indexOf("/") != -1) {
			pos = path.indexOf("/WEB-INF/");
		} else if (path.indexOf("\\") != -1) {
			pos = path.indexOf("\\WEB-INF\\");
		}
		if (pos != -1) {
			path = path.substring(0, pos);
			if (path.indexOf("/") != -1) {
				path = path + "/updateDB/";
			} else if (path.indexOf("\\") != -1) {
				path = path + "\\updateDB\\";
			}
		}
		String osName = System.getProperty("os.name");
		if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
			path = path.replaceFirst("/", "");
		}
	    return path;
	}
	
	public String getClassPath() throws Exception{
		String path = getClass().getClassLoader().getResource("").getPath().replaceAll("%20", " ");
		int pos = -1;
		if (path.indexOf("/") != -1) {
			pos = path.indexOf("/WEB-INF/");
		} else if (path.indexOf("\\") != -1) {
			pos = path.indexOf("\\WEB-INF\\");
		}
		if (pos != -1) {
			path = path.substring(0, pos);
			if (path.indexOf("/") != -1) {
				path = path + "/WEB-INF/classes/";
			} else if (path.indexOf("\\") != -1) {
				path = path + "\\WEB-INF\\classes\\";
			}
		}
		String osName = System.getProperty("os.name");
		if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
			path = path.replaceFirst("/", "");
		}
	    return path;
	}
	
}

