package hackathon.rintis.scheduler;

import hackathon.rintis.externalAPI.ExternalApi;
import hackathon.rintis.helper.TemplateService;
import hackathon.rintis.model.DTO.KolosalResponse;
import hackathon.rintis.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InsightScheduler {

    @Autowired
    private final TemplateService templateService;

    @Autowired
    private final ExternalApi apiCall;

    @Autowired
    private final TransactionRepo transactionRepo;

    public InsightScheduler(TemplateService templateService, ExternalApi apiCall, TransactionRepo transactionRepo) {
        this.templateService = templateService;
        this.apiCall = apiCall;
        this.transactionRepo = transactionRepo;
    }

//    @Scheduled(cron = "0 0 3 * * *")
    public List<Map<String, Object>> getInsightDaily(Integer userId) {

        Map<String, String> params = new HashMap<>();

        String data = transactionRepo.getDataInsightDaily(userId);
        params.put("DATA_INPUT", data);

        String prompt = templateService.generateMessage(3, params);

        KolosalResponse response = apiCall.createChat(prompt);

        ObjectMapper mapper = new ObjectMapper();

        String content = response.getChoices().get(0).getMessage().getContent();

        return mapper.readValue(content, new TypeReference<>() {
        });
    }

}
