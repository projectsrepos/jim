/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.performance;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月27日
 * @version 1.0.0
 */
public interface MsgHandleInterface {
	
	/**
	 * handle after client arrived msg ID
	 * 
	 * @param Msg_ID
	 */
	void handle(String Msg_ID,String topic);
}
