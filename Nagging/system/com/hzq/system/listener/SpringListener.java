package com.hzq.system.listener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hzq.common.util.SpringContextUtils;
import com.hzq.system.service.UpdateDBService;


@Component
public class SpringListener implements ApplicationListener<ContextRefreshedEvent> {
	private static boolean isStart = false;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(!isStart){
//			执行更新数据库方法
			try {
				UpdateDBService updateDBService = (UpdateDBService)SpringContextUtils.getBean("updateDBServiceImpl");
				updateDBService.updateDB();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				isStart = true ;
			}
			
		}
	}

}



