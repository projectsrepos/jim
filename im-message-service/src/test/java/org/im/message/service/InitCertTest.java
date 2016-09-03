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
package org.im.message.service;

import com.jim.im.db.entity.message.AppCert;
import com.jim.im.message.dao.AppCertDao;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.io.InputStream;

/**
 * @version 1.0
 * @since 2016-04-15
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration( MessageServiceServer.class)
//@WebIntegrationTest
public class InitCertTest {


    @Autowired
    AppCertDao appCertDao;

    //@Test
    public void initAppCert() {
        try {
            AppCert cert = new AppCert();
            cert.setAppId("sfa");
            cert.setConnCount(10);
            cert.setProductionCertEnabled(false);
            
            InputStream devIs = InitCertTest.class.getResourceAsStream("/cert/tiaoshi.p12");
            byte [] bs = new byte[devIs.available()];
            devIs.read(bs);
            cert.setDevCert(bs);
            cert.setDevCertPwd("111111");
            
            InputStream productionIs = InitCertTest.class.getResourceAsStream("/cert/fabu.p12");
            byte [] bs2 = new byte[productionIs.available()];
            productionIs.read(bs2);
            cert.setProductionCert(bs2);
            cert.setProductionCertPwd("111111");

            devIs.close();
            productionIs.close();
            
            appCertDao.save(cert);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
