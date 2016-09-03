/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.mqtt;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jim.im.test.mqtt.handle.CommonMsgReceivedHandle;
import com.jim.im.test.mqtt.handle.DefaultMsgReceivedHandle;
import com.jim.im.test.mqtt.handle.IMessageReceivedHandle;

/**  
 * Topic for Handle Map,Support by Pattern
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version 1.0.0   
 * @since 2016年4月12日
 */
public class TopicsHandlesMap {
    private static final HashMap<Pattern, IMessageReceivedHandle> handleMap = 
            new HashMap<Pattern, IMessageReceivedHandle>();
    
    private static final ConcurrentHashMap<String, IMessageReceivedHandle> cachaMap = 
            new ConcurrentHashMap<String, IMessageReceivedHandle>();
    
    
   static {
        handleMap.put(Pattern.compile("/default"), new DefaultMsgReceivedHandle());
        handleMap.put(Pattern.compile("/common"), new CommonMsgReceivedHandle());
        handleMap.put(Pattern.compile("/sfa/hengda/USER_TOPIC"), new CommonMsgReceivedHandle());
    }

    /**
    * get topic Handle  
    * @param topic
    * @return
     */
    public static IMessageReceivedHandle getHandle(String topic){
        IMessageReceivedHandle handle = cachaMap.get(topic);
        if(handle!=null)
            return handle;
        
        for(Entry<Pattern, IMessageReceivedHandle> entry :handleMap.entrySet()){
            Matcher matcher  =entry.getKey().matcher(topic);
            if(matcher.find()){
                cachaMap.put(topic, entry.getValue());
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    
    public static boolean registerTopicHandle(
            String topicPattern,IMessageReceivedHandle handle){
        if(handleMap.containsKey(Pattern.compile(topicPattern))){
            return false;
        }
        handleMap.put(Pattern.compile("/default"), 
                new DefaultMsgReceivedHandle());
        return true;
    }
    
}
