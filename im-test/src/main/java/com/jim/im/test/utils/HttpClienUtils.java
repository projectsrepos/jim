/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**  
 * HttpClien Utils
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version V1.0.0   
 * @since 2016年4月11日 
 */
public class HttpClienUtils {
    private static  HttpClient httpclient = HttpClients.createDefault();
    
    /**
    * http get 请求
    * @param url
    * @return JSONObject
    * @throws ClientProtocolException
    * @throws IOException
     */
    public static JSONObject get(String url) throws ClientProtocolException, IOException{
        HttpGet httpget = new HttpGet(url);  
        HttpResponse resp = httpclient.execute(httpget);
        HttpEntity entity = resp.getEntity();
        String str = EntityUtils.toString(entity);
        JSONObject jsonObj = new JSONObject(str); 
        return jsonObj;
    }
    
    /**
    * http post  请求
    * @param url
    * @param parm
    * @return JSONObject
    * @throws ClientProtocolException
    * @throws IOException
     */
    public static JSONObject post(String url ,Map<String, String> parm) 
            throws ClientProtocolException, IOException{
        HttpPost post = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        for(Entry<String, String> en :parm.entrySet()){
            formparams.add(new BasicNameValuePair(en.getKey(), en.getValue()));  
        }
        
        HttpEntity entity = new UrlEncodedFormEntity(formparams);
        post.setEntity(entity);
        
        HttpResponse resp = httpclient.execute(post);
        
        HttpEntity res = resp.getEntity();
        JSONObject jsonObj = new JSONObject(EntityUtils.toString(res)); 
        return jsonObj; 
    }
    
    /**
    * http post  请求 
    * @param url
    * @param header
    * @param parm
    * @return JSONObject
    * @throws ClientProtocolException
    * @throws IOException
     */
    public static JSONObject post(String url,
            Map<String, String> header 
            ,Map<String, String> parm) 
                    throws ClientProtocolException, IOException{
        HttpPost post = new HttpPost(url);
        
        for(Entry<String, String> entry :parm.entrySet()){
            post.addHeader(entry.getKey(), entry.getValue());
        }
        
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        for(Entry<String, String> en :parm.entrySet()){
            formparams.add(new BasicNameValuePair(en.getKey(), en.getValue()));  
        }
        
        HttpEntity entity = new UrlEncodedFormEntity(formparams);
        post.setEntity(entity);
        
        HttpResponse resp = httpclient.execute(post);
        
        HttpEntity res = resp.getEntity();
        JSONObject jsonObj = new JSONObject(EntityUtils.toString(res)); 
        return jsonObj; 
    }
}
