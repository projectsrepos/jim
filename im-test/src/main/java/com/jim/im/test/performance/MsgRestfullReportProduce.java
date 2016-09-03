/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.performance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月27日
 * @version 1.0.0
 */
@Component
public class MsgRestfullReportProduce{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String msgInfoSql = "insert into perfor_msg_info(str,optime,status) value(?,?,?)";

	private static Log logger = LogFactory.getLog(MsgRestfullReportProduce.class);
	private final String updateReportSql = "UPDATE perfor_msg_report p"
			+"	set p.maxtime=?, p.mintime=?, p.averagetime=?,"
			+" p.clientcount=?"
			+"	where p.id=?";
	
	private final String insertReportSql = "insert into perfor_msg_report(pushid,maxtime,mintime,averagetime,clientcount) value(?,?,?,?,?)";

	private final String maxIDSql = "select MAX(id) maxid from perfor_msg_info";

	final String reportSql = "select c.pushid,min(c.optime) mintime,"
								+" MAX(c.optime) maxtime,COUNT(c.id) clientcount,"
								+" sum(c.optime) totaltime from ("
								+" select b.id pushid,a.id,a.optime-b.optime optime"
								+" from perfor_msg_info a inner JOIN perfor_push_info b "
								+" on a.str=b.str where a.id <?) c GROUP BY c.pushid";

	final String deleteArriveSql = "delete FROM perfor_msg_info where id<";

	final String selectPushSql = "select id,mintime,maxtime,averagetime,clientcount from perfor_msg_report where pushid =?";
	
	protected void updateReport() {
		final Long maxID = getMaxID();
		if(maxID == null || maxID == 0)
			return;
		
//		updatePushid(maxID);
	
		List<Map<String,Object>> result = selectReport(maxID);
		
		if(result == null){
			logger.error("report sql select faild! return size is 0 for maxID :"+maxID);
			return;
		}
		
		dataToParam(result);
		deleteArrive(maxID);
	}
	
	Long getMaxID(){
		return jdbcTemplate.queryForObject(maxIDSql, Long.class);
	}
	
	void updatePushid(long maxID){
		
//		jdbcTemplate.update(updatePushID, maxID);
	}

	List<Map<String,Object>> selectReport(long maxID){
		return jdbcTemplate.queryForList(reportSql, maxID);
	}
	
	void dataToParam(List<Map<String,Object>> result){
		Map<String,Object> push = null;
		for(Map<String,Object> report : result){
			int pushid = (int) report.get("pushid");
			long mintime = (long) report.get("mintime");
			long maxtime = (long) report.get("maxtime");
			long clientcount = (long) report.get("clientcount");
			long totaltime = ((BigDecimal) report.get("totaltime")).longValue();
			
			List<Map<String,Object>> ls = jdbcTemplate.queryForList(selectPushSql, pushid);
			if(ls == null || ls.size() == 0){
				Object[] obj ={pushid,maxtime,mintime,totaltime/clientcount,clientcount};
				jdbcTemplate.update(insertReportSql, obj);
				continue;
			}
			push = ls.get(0);
			int omintime = (int) push.get("mintime");
			int omaxtime = (int) push.get("maxtime");
			int oclientcount = (int) push.get("clientcount");
			int averagetime = (int) push.getOrDefault("averagetime",0);
			
			if(omintime !=0 && omintime<mintime){
				mintime = omintime;
			}
			
			if(omaxtime != 0 && omaxtime>maxtime)
				maxtime = omaxtime;
			
			averagetime = (int) ((averagetime*oclientcount+totaltime)/(oclientcount+clientcount));
			clientcount +=oclientcount;
			Object[] arr = {maxtime,mintime,averagetime,clientcount,pushid};
			jdbcTemplate.update(updateReportSql, arr);
		}
	}
	
	void deleteArrive(Object maxID){
		jdbcTemplate.execute(deleteArriveSql+maxID);
	}
	

	protected void batchStore(String sql, List<Object[]> paramList) {
		jdbcTemplate.batchUpdate(sql, paramList);
		paramList.clear();
	}
	
	void batchInsert(List<Object[]> paramList){
		batchStore(msgInfoSql, paramList);
	}
}
