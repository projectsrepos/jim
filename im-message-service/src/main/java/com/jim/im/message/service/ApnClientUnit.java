/*
 * Copyright 2014 Jim. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jim.im.message.service;

import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.jim.im.TenantSpi;
import com.jim.im.db.entity.message.AppCert;
import com.jim.im.exception.message.ApnCertException;
import com.jim.im.exception.message.ApnPushException;
import com.jim.im.message.apn.ApnEnv;
import com.jim.im.message.dao.AppCertDao;
import com.jim.im.mongo.entity.msg.CommMessage;
import com.jim.im.mongo.entity.msg.IosMessage;
import com.jim.im.utils.Assert;
import com.jim.im.utils.StringUtil;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 使用pushy的apnClient进行消息发送，每个app对应一个apnClient
 * 按msg的appid选择对应的apnClient进行发送
 * 
 * @version 1.0.0
 */
@Component
public class ApnClientUnit {

    public static Logger logger = LoggerFactory.getLogger(ApnClientUnit.class);
            
    Map<String, ApnsClient<SimpleApnsPushNotification>> apnClients;
    Map<String, Future<Void>> connectFutures = new HashMap<String, Future<Void>>();
    List<Thread> connectTasks = new ArrayList<Thread>();

    @Autowired
    AppCertDao appCertDao;
    @Autowired
    TenantSpi tenantSpi;

    @PostConstruct
    public void initApnClients() {
        apnClients = new HashMap<>(tenantSpi.getAppIdCount());

        for (String appId : tenantSpi.getAllAppId()) {
            AppCert example = new AppCert();
            example.setAppId(appId);
            List<AppCert> certs = appCertDao.findByExample(example);
            if (certs == null || certs.size() == 0) {
                continue;
            }
            AppCert cert = certs.get(0);
            final ApnEnv env = cert.isProductionCertEnabled() ? ApnEnv.ProductionEvn : ApnEnv.DevEnv;
            String pwd = cert.isProductionCertEnabled() ? cert.getProductionCertPwd() : cert.getDevCertPwd();
            byte[] bytes = cert.isProductionCertEnabled() ? cert.getProductionCert() : cert.getDevCert();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            startApnsClient(appId, env, pwd, bis);
        }
    }

    public void sendMsg(IosMessage msg) throws ApnCertException, ApnPushException, InterruptedException {
        Assert.notNull(apnClients);
        ApnsClient<SimpleApnsPushNotification> apnsClient = apnClients.get(msg.getAppId());
        if (apnsClient == null) {
            throw new ApnCertException("无apn证书或证书错误,appId:" + msg.getAppId());
        }
        
        waitApnClientConnect(msg, apnsClient);
        //TODO 暂时写死topic
        String apnTopic = "com.GuangZhouXuanWu.iphoneEtion";
        SimpleApnsPushNotification pushNotification =
                new SimpleApnsPushNotification(msg.getToken(), apnTopic, buildApnPayload(msg.getPushContent(), msg.toClientMessage()));
        Future<PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture =
                apnsClient.sendNotification(pushNotification);
        
        try {
            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                    sendNotificationFuture.get();
            if (pushNotificationResponse.isAccepted()) {
                return;
            } else {
                throw new ApnPushException("Notification rejected by the APNs gateway, msgId: " + msg.getId() + "reason: " +
                        pushNotificationResponse.getRejectionReason());
            }
        } catch (final ExecutionException e) {
            throw new ApnPushException("Failed to send push notification because of ExecutionException, msgId: " + msg.getId() , e);
        }
    
    }

    private String buildApnPayload(String tittle, CommMessage msg) {
        if (StringUtil.isEmpty(tittle)) tittle = "您有一条新的消息。";
        ApnsPayloadBuilder builder = new ApnsPayloadBuilder();
        builder.setAlertBody(tittle)
               .addCustomProperty("c", msg.getContent());
        return builder.buildWithDefaultMaximumLength();
    }

    public void destroy() {
        connectTasks.forEach(Thread:: interrupt);
    }

    /**
     * @param appId
     * @param env
     * @param pwd
     * @param bis
     */
    private void startApnsClient(String appId, final ApnEnv env, String pwd, InputStream bis) {
        try {
            final ApnsClient<SimpleApnsPushNotification> apnsClient = new ApnsClient<>(bis, pwd);
            Thread task = new Thread(new ApnClientConnectTask(apnsClient, env.getHost(), appId));
            task.setName("ApnClientConnectTask_appId:" + appId);
            task.start();
            connectTasks.add(task);
            apnClients.put(appId, apnsClient);
        } catch (SSLException e) {
            logger.error("failed to get SSLContext, exist. appId: " + appId, e);
            System.exit(1);
        }
    }
    
    /**
     * @param msg
     * @param apnsClient
     * @throws InterruptedException
     */
    private void waitApnClientConnect(IosMessage msg, ApnsClient<SimpleApnsPushNotification> apnsClient) throws InterruptedException {
        if (!apnsClient.isConnected()) {
            Future<Void> future = null;
            while (true) {
               future = connectFutures.get(msg.getAppId());
               if (future == null) {
                   try {
                       TimeUnit.SECONDS.sleep(10);
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   }
               } else {
                   break;
               }
            }
            future.await();
        }
    }
    
    class ApnClientConnectTask implements Runnable {
        ApnsClient<?> apnClient;
        String host;
        String appId;
        
        ApnClientConnectTask(ApnsClient<?> apnsClient, String host, String appId) {
            this.apnClient = apnsClient;
            this.host = host;
            this.appId = appId;
        }
        
        @Override
        public void run() {
            try {
                Future<Void> connectFuture = apnClient.connect(host);
                connectFutures.put(appId, connectFuture);
                connectFuture.await();
            } catch (InterruptedException e) {
                try {
                    apnClient.disconnect().await();
                } catch (InterruptedException e1) {
                    //ignore it
                }
            }
        }
    }
}
