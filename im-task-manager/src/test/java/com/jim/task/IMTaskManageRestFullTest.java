/*
 */
package com.jim.task;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronExpression;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.jim.im.exception.ImException;
import com.jim.im.task.IMTaskManagerServer;
import com.jim.im.task.entity.TaskInfo;

/**
 * 
 * @version 1.0.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(IMTaskManagerServer.class)
//@WebIntegrationTest
public class IMTaskManageRestFullTest {
	private RestTemplate template = new TestRestTemplate();

	private String getServerUrl = "http://localhost:8094/api/im/taskManager/pushmsgTimer";
	
//	@Test
	public void test() throws ParseException{
		new CronExpression("111");
	}
	/**
	 * appid is null
	 */
//	@Test
	public void pushmsgTimerTest1(){
		TaskInfo info = new TaskInfo();
		info.setAppId("sfa");
		info.setTenantId("hengda");
		info.setOperatorid(642693);
		info.setPushType("user");
		info.setPushIds("642693");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test1");
		info.setContent("test1");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println(str.getBody());
	}
	
	/**
	 * TenantId is null
	 */
//	@Test
	public void pushmsgTimerTest2(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test2");
		info.setContent("test2");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test2："+str.getBody());
	}
	
	/**
	 * Operatorid is null
	 */
//	@Test
	public void pushmsgTimerTest3(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(null);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test3");
		info.setContent("test3");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test3："+str.getBody());
	}
	
	/**
	 * PushType is null
	 */
//	@Test
	public void pushmsgTimerTest4(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test4");
		info.setContent("test4");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test4："+str.getBody());
	}
	
	
	/**
	 * PushIds is null
	 */
//	@Test
	public void pushmsgTimerTest5(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test5");
		info.setContent("test5");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test5："+str.getBody());
	}
	
	
	/**
	 * MsgType is null
	 */
//	@Test
	public void pushmsgTimerTest6(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test6");
		info.setContent("test6");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test6："+str.getBody());
	}
	
	
	/**
	 * TimerStrategy is null
	 */
//	@Test
	public void pushmsgTimerTest7(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("");
		info.setPushContent("test7");
		info.setContent("test7");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("tes7："+str.getBody());
	}
	
	
	/**
	 * PushContent is null
	 */
//	@Test
	public void pushmsgTimerTest8(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("");
		info.setContent("test8");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test8："+str.getBody());
	}
	
	
	/**
	 * PushType out list{user,group}
	 */
//	@Test
	public void pushmsgTimerTest9(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("java");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test9");
		info.setContent("test9");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test9："+str.getBody());
	}
	
	
	/**
	 * MsgType out List{PUSH,CHAT,COMMON,SYSTEM}
	 */
//	@Test
	public void pushmsgTimerTest10(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test10");
		info.setContent("test10");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test10："+str.getBody());
	}
	
	/**
	 * TimerStrategy is valid
	 */
//	@Test
	public void pushmsgTimerTest11(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * * ? ?");
		info.setPushContent("test11");
		info.setContent("test11");
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test11："+str.getBody());
	}
	
	
	/**
	 * startTime < now
	 */
//	@Test
	public void pushmsgTimerTest12(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test12");
		info.setContent("test12");
		info.setStartTime(new Date(System.currentTimeMillis()-100000));
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test12："+str.getBody());
	}
	
	/**
	 * startTime > endTime
	 */
//	@Test
	public void pushmsgTimerTest13(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test13");
		info.setContent("test13");
		info.setStartTime(new Date(System.currentTimeMillis()+100000));
		info.setEndTime(new Date(System.currentTimeMillis()+10000));
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test13："+str.getBody());
	}
	
	
//	@Test
	public void pushmsgTimerTest14(){
		TaskInfo info = new TaskInfo();
		info.setAppId("1");
		info.setTenantId("1");
		info.setOperatorid(1);
		info.setPushType("user");
		info.setPushIds("121212,11111");
		info.setTimerStrategy("0/2 * * * * ?");
		info.setPushContent("test13");
		info.setContent("test13");
		info.setStartTime(new Date(System.currentTimeMillis()+10000));
		info.setEndTime(new Date(System.currentTimeMillis()+100000));
		ResponseEntity<String> str =  template.postForEntity(getServerUrl, info, String.class);
		System.out.println("test14："+str.getBody());
	}
	
}
