import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

public class Main {
    public static ObjectMapper mapper = new ObjectMapper();
    private static final String uri = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(uri);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            List<Post> posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Post>>() {
            });
            posts.stream()
                    .filter(x -> x.getUpVotes() != null && x.getUpVotes() > 0)
                    .forEach(facts -> System.out.println(facts.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
