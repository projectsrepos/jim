/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.performance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月16日
 * @version 1.0.0
 */
@Component
public class ReportDataStoreService {

	private static String msgInfoSql = "insert into perfor_msg_info(str,optime,status) value(?,?,?)";

	private static Log logger = LogFactory.getLog(ReportDataStoreService.class);
	
	@Autowired
	private ArriveReportProduce arriveProduce;
	
	@Autowired
	private MsgRestfullReportProduce msgProduce;
	
	@Value("${isBootStartMonitor}")
	private boolean isBootStartMonitor;
	
	@PostConstruct
	public void initStore() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				storeArriveInfo();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				storeMsgInfo();
			}
		}).start();
		
		if(isBootStartMonitor)
			reportMonitor();
	}
	
	private void storeMsgInfo(){
		List<Object[]> ls = new ArrayList<Object[]>();
		while (true) {
			try {
				Object[] str = MessageContextHandle.msgQueue.poll();
				if (str == null) {
					if (ls.size() > 0)
						msgProduce.batchInsert(ls);
					Thread.currentThread().sleep(30000);
				} else {
					ls.add(str);
					if (ls.size() > 100)
						msgProduce.batchInsert(ls);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reportMonitor(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						logger.info("updateReport starting!");
						arriveProduce.updateReport();
						msgProduce.updateReport();
						logger.info("updateReport end!");
						Thread.currentThread().sleep(1000*60*5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	protected void storeArriveInfo() {
		List<Object[]> ls = new ArrayList<Object[]>();
		while (true) {
			try {
				Object[] str = MqttPerformanceClient.arriveQueue.poll();
				if (str == null) {
					if (ls.size() > 0)
						arriveProduce.batchInsert(ls);
					Thread.currentThread().sleep(30000);
				} else {
					ls.add(str);
					if (ls.size() > 100)
						arriveProduce.batchInsert(ls);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
