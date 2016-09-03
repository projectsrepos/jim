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
package com.jim.im.login;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Unit test for simple App.
 */
public class GatawayServiceTest {
	public static void main(String[] args) throws UnknownHostException//, AppException 
	{
/*
		 ProtocolRegister.registerProcotol(
                CommandIDs.vlsp_maUserManagerService.loginbymobileMethod,
                null,
                new UserManagerService_loginbymobileRequest(),
                new UserManagerService_loginbymobileResponse());
        
		  ProtocolRegister.registerProcotol(
				  CommandIDs.vlsp_maUserManagerService.updatetokenMethod,
                 null,
                 new UserManagerService_updatetokenRequest(),
                 new UserManagerService_updatetokenResponse());
		  
		  ProtocolRegister.registerProcotol(
	                CommandIDs.vlsp_eaEntSystemManagerService.getuserstatusMethod,
	                null,
	                new EntSystemManagerService_getuserstatusRequest(),
	                new EntSystemManagerService_getuserstatusResponse());
	        
		
        
        UserManagerService service = new UserManagerService(
                InetAddress.getByName("172.16.0.36"), 10320);
        registerobj obj = service.loginbymobile("1592015269303", "1008728");
        
        System.out.println(obj.sessionid);
       */
        
//        boolean bool= service.updatetoken(obj.sessionid, "token111", null);
//        System.out.println(bool);
//        
//        EntSystemManagerService entService = new EntSystemManagerService(
//	                InetAddress.getByName("172.16.0.36"), 10320);
//        dictionaryobj[] paras = new dictionaryobj[1];
//        paras[0] = new dictionaryobj();
//        paras[0].Itemcode = "usernumbers";
//        paras[0].Itemname = String.valueOf(obj.usernumber);
//        rowobj[] rowobjs = entService.getuserstatus(obj.sessionid, obj.usernumber, paras);
//        if(rowobjs != null && rowobjs.length != 0) {
//       	 dictionaryobj[] objs =  rowobjs[0].Values;
//       	 for(dictionaryobj retobj: objs) {
//       		 System.out.println(retobj.Itemcode + ":" + retobj.Itemname);
//       	 }
//        }
	
	}
}
