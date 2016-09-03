/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 *
 *
 */

package com.jim.im.test.performance;

import com.google.common.collect.Maps;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.model.TimeConsuming;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.test.utils.MD5Utils;
import com.jim.im.utils.ImUtils;
import com.jim.im.utils.JsonUtil;
import com.jim.im.utils.StringUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:linjianfei@wxchina.com">linjianfei</a>
 * @version 1.0
 * @since 2016-06-29
 */
@Component
public class MqttClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttClientTest.class);

    @Value("${broker_address}")
    private String brokerAddress;
    @Value("${offlineMassagesURL}")
    private String offlineMassagesURL;
    private int upLimit = 2000;

    private final ConcurrentMap<Integer, MqttClient> clientMap = Maps.newConcurrentMap();
    private final String[] topicRepository = new String[upLimit];
    private final Random randomGenerator = new Random(System.currentTimeMillis());
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ExecutorService storeService = Executors.newFixedThreadPool(2);

//    @PostConstruct
    public void init() {
        // 初始化Mqtt客户端
        initClients(1000);
        // 初始化主题仓库
        initTopicRepository(1000);
        // 初始化随机订阅主题
        randomSubscribe();
        // 启动随机发送机制
        randomSendMsg();
    }

    private void randomSendMsg() {
        while (true) {
            int willSendClientNum = getRate();
            for (int index = 0; index < willSendClientNum; index++) {
                MqttClient client = clientMap.get(getRate());
                executorService.execute(new SendTask(client));
            }
        }
    }

    class SendTask implements Runnable {
        private MqttClient mqttClient;

        SendTask(MqttClient client) {
            this.mqttClient = client;
        }

        @Override
        public void run() {
            try {
                String content = BodyProduce.generateDynamicLengthContent(20);
                mqttClient.publish(topicRepository[getRate()], content.getBytes(), 0, false);
                TimeConsuming timeConsuming = new TimeConsuming(MD5Utils.MD5(content), System.currentTimeMillis());
                storeService.execute(new StoreTask(timeConsuming));
            } catch (MqttException e) {
                LOGGER.error("Send message failure.", e);
            }
        }
    }

    private void randomSubscribe() {
        for (Map.Entry<Integer, MqttClient> entry : clientMap.entrySet()) {
            int subNum = getRate();
            doSubscribe(subNum, entry.getValue());
        }
    }

    private void doSubscribe(int subNum, MqttClient client) {
        for (int index = 0; index < subNum; index++) {
            String topicName = topicRepository[getRate()];
            try {
                client.subscribe(topicName);
            } catch (MqttException e) {
                LOGGER.error("Subscribe topic failure. --> " + topicName, e);
            }
        }
    }

    private void initTopicRepository(Integer from) {
        Integer userIdIndex = from;
        for (int index = 0; index < topicRepository.length; index++) {
            List<String> topicNames =
                    ImUtils.generateTopicName(userIdIndex, "sfa", "hengda", TopicOwnerType.USER, TopicType.PUSH);
            topicRepository[index] = topicNames.get(0);
            userIdIndex++;
        }
    }

    private void initClients(int from) {
        int userIndex = from;
        for (int index = 0; index < upLimit; index++) {
            clientMap.put(userIndex, buildMqttClient(userIndex));
            userIndex++;
        }
    }

    private int getRate() {
        return randomGenerator.nextInt(upLimit);
    }

    private int[] getRates(int size) {
        int[] randoms = new int[size];
        for (int index = 0; index < size; index++) {
            randoms[index] = getRate();
        }

        return randoms;
    }

    private MqttClient buildMqttClient(Integer userId) {
        MqttClient client = null;
        try {
            MqttClientPersistence persistence = new MemoryPersistence();
            client = new MqttClient(brokerAddress, userId.toString(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            connOpts.setKeepAliveInterval(3600); // 保活一个人小时
            client.setCallback(new InnerMqttCallback(userId)); // 设置回调
            client.connect(connOpts);
        } catch (MqttException e) {
            LOGGER.error("Generate MQtt client is failure.", e);
        }
        return client;
    }

    class InnerMqttCallback implements MqttCallback {
        private Integer clientId;

        public InnerMqttCallback(Integer clientId) {
            this.clientId = clientId;
        }

        public void messageArrived(String topic, MqttMessage message) throws Exception {
            long arriveTime = System.currentTimeMillis();
            String messageId = message.getPayload().toString();
            handle(messageId, topic);
        }

        public void deliveryComplete(IMqttDeliveryToken token) {
            // ignore
        }

        public void connectionLost(Throwable cause) {
            // You could try to reconnecting server.
        }

        public void handle(String msgId, String topic) {
            try {
                Client client= JerseyClientBuilder.newBuilder().build();
                Map<String ,Object> header = new HashMap<String, Object>();
                header.put("appId", "sfa");
                header.put("tenantId", "hengda");
                header.put("operatorId", clientId);

                Map<String, Object> body = new HashMap<String, Object>();
                body.put("lastMsgId", msgId);
                String[] topics = {topic};
                body.put("topics",topics);
                Response rsp = client.target(offlineMassagesURL)
                                     .request()
                                     .accept(MediaType.APPLICATION_JSON)
                                     .headers(new MultivaluedHashMap<String, Object>(header))
                                     .buildPost(Entity.entity(body, MediaType.APPLICATION_JSON))
                                     .invoke();
                HashMap<String, String> ret = rsp.readEntity(HashMap.class);
                TimeConsuming timeConsuming = new TimeConsuming();
                timeConsuming.setTimePoint(System.currentTimeMillis());
                if (!"1".equals(ret.get("status").toString())) {
                    String data = ret.get("data");
                    if (StringUtil.isNoneBlank(data)) {
                        List<ImMessage> imMessages = JsonUtil.jsonToList(data, ImMessage.class);
                        if (imMessages.size() > 0) {
                            String content = imMessages.get(0).getContent();
                            timeConsuming.setMdtContent(MD5Utils.MD5(content));
                        }
                    }
                }
                storeService.execute(new StoreTask(new TimeConsuming()));
            } catch (Exception e) {
                LOGGER.error("处理消息失败.", e);
            }
        }
    }

    class StoreTask implements Runnable {
        private TimeConsuming timeConsuming;

        public StoreTask(TimeConsuming timeConsuming) {
            this.timeConsuming = timeConsuming;
        }

        @Override
        public void run() {
            // 入库
        }
    }
}
