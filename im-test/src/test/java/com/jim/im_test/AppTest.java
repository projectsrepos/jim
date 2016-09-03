package com.jim.im_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	
	private static SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String brokerLogPath = "C:\\Users\\Administrator\\Downloads\\info.log";
	
	private static String outFilePath = "C:\\Users\\Administrator\\Desktop\\im-test-run\\test_report_";
	
	
	
	public static void main(String[] args) throws ParseException{
		Map<String,Object> loginRPCLogMap = brokerLogParse(brokerLogPath);
	}
	
	
	private static Map<String,Object> brokerLogParse(String logPath){
		List<String> timeList = new ArrayList<String>();
		try {
			File file = new File(logPath);
			FileReader reader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(reader);
			String str= null;
			long count =1;
			long time1000 = 0;
			long time5000 =0;
			long time10000=0;
			List<String> ls = new ArrayList<String>();
			List<String> ls1 = new ArrayList<String>();
			List<String> ls2 = new ArrayList<String>();
			while((str = bReader.readLine())!=null){
				String[] s = str.split(" ", 3);
				long curTime = format.parse(s[0]+" "+s[1]).getTime();
				int i = str.indexOf("OFFLINE");
				if(i>-1){
					continue;
				}
				if(count % 1000 ==0){
					ls.add(count+"\t"+(count==0?0:curTime-time1000));
				}else if(count % 1000 ==1){
					time1000= curTime;
				}
				
				if(count % 5000 ==0){
					ls1.add(count+"\t"+(count==0?0:curTime-time5000));
				}else if(count % 5000 ==1){
					time5000= curTime;
				}
				
				if(count % 10000 ==0){
					ls2.add(count+"\t"+(count==0?0:curTime-time10000));
					
				}else if(count % 10000 ==1){
					time10000 = curTime;
				}
				String timestr = str.substring(0, str.indexOf(","));
				timeList.add(timestr);
				count++;
			}
			
			FileWriter out = new FileWriter(outFilePath+new Date().getTime()+".txt");
			BufferedWriter writer = new BufferedWriter(out);
			writer.write("per 1000 connect request\r\n");
			foreachArray(ls,writer);
			writer.write("per 5000 connect request\r\n");
			foreachArray(ls1,writer);
			writer.write("per 10000 connect request\r\n");
			foreachArray(ls2,writer);
			
			writer.write("per second request\r\n");
			String st = null;
			Collections.sort(timeList);
			int count1 = 0;
			long row = 0;
			for(String s : timeList){
				if(st == null || !s.equals(st)){
					writer.write(count1+"\t");
					if (row % 100== 0)
						writer.write(row+"\t");
					
					st =s;
					count1 = 0;
					writer.write("\r\n"+st+"\t");
				}
				count1++;
				row++;
			}
			
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static void foreachArray(Collection<String> ls,Writer writer) throws IOException{
		for(Object obj : ls){
			writer.write(obj.toString()+"\r\n");
		}
	}
}
