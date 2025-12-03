package hackathon.rintis.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${kolosal.url}")
    private String kolosalUrl;

    @Value("${kolosal.key}")
    private String api_key;

    @Bean
    public WebClient kolosalWebClient() {
        return WebClient
                .builder()
                .baseUrl(kolosalUrl)
                .defaultHeader("Authorization", "Bearer " + api_key)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
