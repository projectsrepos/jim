/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.rest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.jim.im.api.LoginRpcService;
import com.jim.im.db.entity.login.TerminalSessionInfo;
import com.jim.im.exception.ImException;
import com.jim.im.rest.response.RestResult;
import com.jim.im.test.entity.MqttCommandEntity;
import com.jim.im.test.mqtt.MqttClientFactory;
import com.jim.im.test.performance.MqttPerformanceClient;
import com.jim.im.test.performance.MsgHandleInterface;
import com.jim.im.test.performance.PushmsgPerformanceClient;
import com.jim.im.test.performance.ReportDataStoreService;
import com.jim.im.test.performance.TestStrategyThread;
import com.jim.im.test.utils.HttpClienUtils;

/**
 * Test Model restfull api
 * 
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version V1.0.0
 * @since 2016年4月6日
 */
@Component
@Path("/")
public class TestService {
	@Autowired
	private LoginRpcService loginService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private PushmsgPerformanceClient pushmsgClient;
	
	@Autowired
	private MqttPerformanceClient mqttPerformanceClient;
	
	@Autowired
	private ReportDataStoreService dataService;
	
	private static SimpleDateFormat format = new  SimpleDateFormat("yyyyMMddHHmmss"); 
	
	@Autowired
	private MsgHandleInterface msgHandle;
	
	/**
	 * 注册msgID 处理方法
	 * @return
	 */
	@GET
	@Path("registerMsgHandle")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerMsgHandle(){
		mqttPerformanceClient.handleList.add(msgHandle);
		return RestResult.success();
	}
	
	@GET
	@Path("checkIsConnected")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkIsConnected(){
		return RestResult.success(mqttPerformanceClient.checkIsConnected());
	}
	
	@POST
	@Path("testStrategy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testStrategy(@QueryParam(value="initConnect") long initConnect,
			@QueryParam(value="maxConnect") long maxConnect,
			@QueryParam(value="interval") int interval,
			@QueryParam(value="clientIps") String clientIps
			){
		String[] str = clientIps.split(",");
		TestStrategyThread strategyThread = 
				new TestStrategyThread(
						initConnect, maxConnect, interval, str,pushmsgClient);
		strategyThread.start();
		return RestResult.success();
	}
	
	/**
	 * 性能测试 关闭所以的测试连接
	 * @return
	 */
	@GET
	@Path("closeAllMqttClient")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response closeAllMqttClient(){
		mqttPerformanceClient.closeAll();
		return RestResult.success();
	}
	
	/**
	 * 性能测试 检测连接初始化程度
	 * @return
	 */
	@GET
	@Path("checkInitConnected")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkInitConnected(){
		if(mqttPerformanceClient.isInitConnect == 2){
			return RestResult.success(mqttPerformanceClient.isInitConnect);
		}
		return RestResult.failure("5000", mqttPerformanceClient.isInitConnect+"" );
	}
	
	/**
	 * 性能测试  mqtt客户端连接
	 * @param connectcount 连接个数
	 * @return
	 */
	@GET
	@Path("connectMqtt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response connectMqtt(
			@QueryParam(value="connectcount") long connectcount,
			@QueryParam(value="preClientID") String preClientID,
			@QueryParam(value="subID") String subID){
		if(connectcount == 0)
			RestResult.failure("5000", "connectcount is 0");
		
		if(pushmsgClient.pushThreadList.size()>0){
			return RestResult.failure("4000", 
					"already exists push msg thread is running! please call stoppush");
		}
		
		mqttPerformanceClient.connectMqtt(preClientID,connectcount,subID);
		return RestResult.success();
	}
	
	@GET
	@Path("stoppush")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response stoppush(){
		pushmsgClient.stoppush();
		return RestResult.success();
	}
	
	
	/**
	 * 性能测试 消息推送
	 * @param pushmsgCount 消息推送次数
	 * @param bodysize 推送消息长度（/个汉字）
	 * @param interval 推送间隔（毫秒）
	 * @return
	 * @throws ImException
	 * @throws JMSException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GET
	@Path("pushmsgCount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pushmsgCount(
			@QueryParam(value="bodysize") int bodysize,
			@QueryParam(value="interval") int interval,
			@QueryParam(value="receiverIds") String receiverIds) 
			throws ImException, JMSException,
			ClientProtocolException, IOException {
		if(pushmsgClient.pushThreadList.size()>0){
			return RestResult.failure("4000", 
					"already exists push msg thread is running! please call stoppush");
		}
		
		pushmsgClient.pushmsgCountRun(bodysize,interval,receiverIds);
		return RestResult.success();
	}
	
	/**
	 * 性能测试 报表数据处理线程启动
	 * @return
	 */
	@GET
	@Path("reportMonitor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reportMonitor() {
		dataService.reportMonitor();
		return RestResult.success();
	}
	
	
	/**
	 * @Title: getServer
	 * @Description: 4.2.2.2.1 获取连接服务器IP接口
	 * @param sessionID
	 * @throws ImException
	 * @return Response
	 * @throws JMSException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@POST
	@Path("testRPC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testRPC() throws ImException, JMSException,
			ClientProtocolException, IOException {
		for (int i = 0; i < 100; i++) {
			TerminalSessionInfo status = loginService.checkConnectStatus(
					"crm_tenent1_" + String.valueOf(i));
		}
		return RestResult.success("11", 1);
	}

	/**
	 * test Http remote(restfull api)
	 * 
	 * @return
	 * @throws ImException
	 * @throws JMSException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@POST
	@Path("testHttp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHttp() throws ImException, JMSException,
			ClientProtocolException, IOException {
		JSONObject obj = HttpClienUtils
				.get("http://localhost:8090/api/im/loginService/getServer?sessionID=123");
		return RestResult.success("11", obj);
	}

	/**
	 * mqttClient command handle restfull
	 * 
	 * @return
	 * @throws ImException
	 * @throws JMSException
	 */
	@POST
	@Path("mqttClient")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response mqttClient(@RequestParam MqttCommandEntity command)
			throws ImException, JMSException {
		try {

			Method method = this.getClass().getDeclaredMethod(
					command.getCommand(), MqttCommandEntity.class);

			method.invoke(this, command);

			redisTemplate.opsForList()
					.leftPush(
							MqttClientFactory.redisPrefix.concat(command
									.getClientID()), command.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(MqttClientFactory.redisPrefix,
				redisTemplate.opsForList().range(
						MqttClientFactory.redisPrefix.concat(command
								.getClientID()), 1, 10));

		map.put(MqttClientFactory.redisArrivedPrefix,
				redisTemplate.opsForList().range(
						MqttClientFactory.redisArrivedPrefix.concat(command
								.getClientID()), 1, 10));
		return RestResult.success("1", map);
	}

	/**
	 * mqtt command publish handle
	 * 
	 * @param command
	 */
	private void publish(MqttCommandEntity command) {
		try {
			MqttClient client = MqttClientFactory.getMqttClient(
					command.getUrl(), command.getClientID(), redisTemplate);

			for (String topic : command.getTopic()) {
				client.publish(topic, command.getContent().getBytes(),
						command.getQos(), command.isRetained());
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * mqtt command subscribe handle
	 * 
	 * @param command
	 */
	private void subscribe(MqttCommandEntity command) {
		try {
			MqttClient client = MqttClientFactory.getMqttClient(
					command.getUrl(), command.getClientID(), redisTemplate);

			client.subscribe(command.getTopic());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * mqtt command close handle
	 * 
	 * @param command
	 */
	private void close(MqttCommandEntity command) {
		try {
			MqttClientFactory.close(command.getUrl(), command.getClientID());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

}
