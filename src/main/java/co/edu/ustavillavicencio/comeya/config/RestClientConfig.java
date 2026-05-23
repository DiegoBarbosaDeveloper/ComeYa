package co.edu.ustavillavicencio.comeya.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient wompiRestClient(
            @Value("${wompi.base-url}") String baseUrl,
            @Value("${wompi.private-key}") String privateKey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + privateKey)
                .build();
    }
}
