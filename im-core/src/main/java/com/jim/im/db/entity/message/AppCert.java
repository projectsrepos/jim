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
package com.jim.im.db.entity.message;

import com.jim.im.db.base.Entity;


/**
 * IOS app 证书、证书配置对象，证书保存数据库
 * 
 * @date 2015年9月21日
 * @version 1.0.0
 */
public class AppCert implements Entity {

	private static final long serialVersionUID = 555379161963003204L;

	private Integer id;
	
	/** app_id */
    private String appId;

	private byte[] productionCert;
	private String productionCertPwd;

	private byte[] devCert;
	private String devCertPwd;

	private Boolean productionCertEnabled;
	private Integer connCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean isProductionCertEnabled() {
		return productionCertEnabled;
	}

	public void setProductionCertEnabled(Boolean productionCertEnabled) {
		this.productionCertEnabled = productionCertEnabled;
	}

	public byte[] getProductionCert() {
		return productionCert;
	}

	public void setProductionCert(byte[] productionCert) {
		this.productionCert = productionCert;
	}

	public String getProductionCertPwd() {
		return productionCertPwd;
	}

	public void setProductionCertPwd(String productionCertPwd) {
		this.productionCertPwd = productionCertPwd;
	}

	public byte[] getDevCert() {
		return devCert;
	}

	public void setDevCert(byte[] devCert) {
		this.devCert = devCert;
	}

	public String getDevCertPwd() {
		return devCertPwd;
	}

	public void setDevCertPwd(String devCertPwd) {
		this.devCertPwd = devCertPwd;
	}

	public Integer getConnCount() {
		return connCount;
	}

	public void setConnCount(Integer connCount) {
		this.connCount = connCount;
	}

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
