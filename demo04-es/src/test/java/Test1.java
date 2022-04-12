import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = Test1.class)
@RunWith(SpringRunner.class)
public class Test1 {

@Test
    public void test1() {
// rest
        String auth = "elastic:123456";
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("192.168.136.129", 9200));
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        restClientBuilder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Authorization", authHeader)});
        RestHighLevelClient restClient = new RestHighLevelClient(restClientBuilder);
        IndexRequest request = new IndexRequest("hotel");
    Map<String, String> map = new HashMap<>();
    map.put("testKey2", "testValue2");

    String jsonString = JSON.toJSONString(map);
    request.source(jsonString, XContentType.JSON);

    try {
            request.id("12345");
        IndexResponse indexResponse = restClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse);

    } catch (IOException e) {
            e.printStackTrace();
        }
//    TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
//            .put("cluster.name", "my-application")
//            .put("xpack.security.user", "elastic:691248")
////            .put("xpack.ssl.key", "/opt/cert/my-application/my-application.key")
////            .put("xpack.ssl.certificate", "/opt/cert/my-application/my-application.crt")
////            .put("xpack.ssl.certificate_authorities", "/opt/cert/ca/ca.crt")
////            .put("xpack.security.transport.ssl.verification_mode", "certificate")
//            .put("xpack.security.transport.ssl.enabled", "true")
//            .build());
    }
    @Test
    public void test2(){
        String auth = "elastic:691248";
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("192.168.136.129", 9200));
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        restClientBuilder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Authorization", authHeader)});
        RestHighLevelClient restClient = new RestHighLevelClient(restClientBuilder);
        try {
            GetRequest getRequest = new GetRequest("hotel");
            getRequest.id("12345");
            GetResponse getResponse = restClient.get(getRequest, RequestOptions.DEFAULT);
            System.out.println(getResponse);
            String sourceAsString = getResponse.getSourceAsString();
            Map hotelDoc = JSON.parseObject(sourceAsString, Map.class);
            System.out.println(hotelDoc);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   @Test
    public void test3(){
       PreBuiltTransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
               .put("client.transport.sniff", "true")
               .put("client.transport.ignore_cluster_name", "true")
               .put("xpack.security.user", "elastic:123456")
               .put("xpack.security.transport.ssl.enabled", "true")
               .put("xpack.security.transport.ssl.verification_mode", "certificate")
               .put("xpack.security.transport.ssl.keystore.path", "D:\\elastic-certificates.p12")
               .put("xpack.security.transport.ssl.truststore.path", "D:\\elastic-certificates.p12")
//               .put("xpack.security.transport.ssl.keystore.password","691248")
//               .put("xpack.security.transport.ssl.truststore.password"," 691248")
               .build());
        try {
       client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.136.129"), 9200));
//        String auth = "elastic:691248";
//        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("192.168.136.129", 9200));
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
//        String authHeader = "Basic " + new String(encodedAuth);
//        restClientBuilder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Authorization", authHeader)});
//        RestHighLevelClient restClient = new RestHighLevelClient(restClientBuilder);
            GetRequest getRequest = new GetRequest("hotel");
            getRequest.id("12345");
            ActionFuture<GetResponse> future = client.get(getRequest);
            GetResponse getResponse = future.get();
//            System.out.println(getResponse);
            String sourceAsString = getResponse.getSourceAsString();
            Map hotelDoc = JSON.parseObject(sourceAsString, Map.class);
            System.out.println(hotelDoc);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
