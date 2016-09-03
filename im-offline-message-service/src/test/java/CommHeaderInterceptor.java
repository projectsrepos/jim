/*
 */

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * 用于单元测试时，插入固定的请求头信息
 * 
 * @version 1.0
 */
public class CommHeaderInterceptor implements ClientHttpRequestInterceptor {
    private Map<String, List<String>> _headers;

    public CommHeaderInterceptor(Map<String, List<String>> headers) {
        _headers = headers;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
        if (this._headers != null) {
            for (Map.Entry<String, List<String>> entry : _headers.entrySet()) {
                headers.put(entry.getKey(), entry.getValue());
            }
        }
        return execution.execute(request, body);

    }
}
