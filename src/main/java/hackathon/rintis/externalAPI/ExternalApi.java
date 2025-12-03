package hackathon.rintis.externalAPI;

import hackathon.rintis.model.DTO.ChatRequest;
import hackathon.rintis.model.DTO.KolosalResponse;
import hackathon.rintis.model.DTO.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class ExternalApi {

    private final WebClient kolosalWebClient;

    public ExternalApi(WebClient kolosalWebClient) {
        this.kolosalWebClient = kolosalWebClient;
    }

    public KolosalResponse createChat(String prompt) {

        ChatRequest request = new ChatRequest(
                "Kimi K2",
                List.of(new Message(
                        "owner",
                        prompt
                        ))
        );

        return kolosalWebClient.post()
                .uri("chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(KolosalResponse.class)
                .block();
    }
}
