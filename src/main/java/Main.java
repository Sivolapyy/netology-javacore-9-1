import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(10000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()) {

            HttpGet request = new HttpGet(
                    "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

            CloseableHttpResponse response = httpClient.execute(request);

            ObjectMapper mapper = new ObjectMapper();
            List<CatsFacts> catsFacts = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });

            catsFacts.stream().filter(fact -> fact.getUpvotes() != null && fact.getUpvotes() > 0)
                    .forEach(fact -> System.out.println(fact.getText() +
                            " (голосов - " + fact.getUpvotes() + ")"));

        } catch (IOException exc) {
            System.out.println("Ошибка - " + exc.getMessage());
        }

    }

}
