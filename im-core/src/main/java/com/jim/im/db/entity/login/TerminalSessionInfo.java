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
package com.jim.im.db.entity.login;

import com.jim.im.consts.OsType;
import com.jim.im.consts.TerminalStatus;
import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * @version 1.0.0
 */
public class TerminalSessionInfo implements Serializable {

	private static final long serialVersionUID = -9053050195172342139L;

	private String userID;
	private UUID sessionID;
	private OsType osType;
	private String deviceToken;
	private TerminalStatus terminalStatus;
	
	public OsType getOsType() {
		return osType;
	}
	public void setOsType(OsType osType) {
		this.osType = osType;
	}
	public UUID getSessionID() {
		return sessionID;
	}
	public void setSessionID(UUID sessionID) {
		this.sessionID = sessionID;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public TerminalStatus getTerminalStatus() {
		return terminalStatus;
	}
	public void setTerminalStatus(TerminalStatus terminalStatus) {
		this.terminalStatus = terminalStatus;
	}
}
