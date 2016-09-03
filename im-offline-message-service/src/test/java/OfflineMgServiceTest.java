/*
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jim.im.exception.ImParamException;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.offline.OfflineMessageServer;
import com.jim.im.offline.service.OfflineMessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OfflineMessageServer.class)
@WebIntegrationTest
public class OfflineMgServiceTest {
    @Autowired
    private OfflineMessageService offlineMessageService;

    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int port;
    @Value("${server.context-path}")
    private String contextPath;

    private String getUrl(String sourcePath) {
        return "http://localhost:" + port + contextPath + sourcePath;
    }

    @Before
    public void init() {
        restTemplate = new TestRestTemplate();

        // 自定义数据
        Map<String, List<String>> headers = Maps.newHashMap();
        headers.put("appId", Lists.newArrayList("sfa"));
        headers.put("tenantId", Lists.newArrayList("hengda"));
        headers.put("operatorId", Lists.newArrayList("1"));
        CommHeaderInterceptor headerInterceptor = new CommHeaderInterceptor(headers);
        restTemplate.getInterceptors().add(headerInterceptor);
    }

    private void printObjectId(Collection<ImMessage> offlineMessages) {
        for (ImMessage message1 : offlineMessages) {
            System.out.println(message1.getId());
        }
    }

    // restful 接口
    public void testGetSubTopics() {
        String result = restTemplate.getForObject(getUrl("/getOfflineMassages"), String.class);
        System.out.println(result);
        Assert.assertNotNull(result, result);
    }

    //@Test
    public void test() throws ImParamException {
        List<String> topics = Lists.newArrayList("/sfa/hengda/USER/642693/PUSH");
        BigInteger id = new BigInteger("578647ecd5decb7b2b30757f", 16);
        Collection<ImMessage> offlineMessages =
                offlineMessageService.getOfflineMessages(topics, id);
        System.out.println(offlineMessages);
    }

    @Test
    public void emptyTest() {

    }
}
