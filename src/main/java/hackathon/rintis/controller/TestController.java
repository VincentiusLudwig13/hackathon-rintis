package hackathon.rintis.controller;

import hackathon.rintis.externalAPI.ExternalApi;
import hackathon.rintis.helper.TemplateService;
import hackathon.rintis.model.DTO.BusinessRequest;
import hackathon.rintis.model.DTO.KolosalResponse;
import hackathon.rintis.model.entity.TransactionList;
import hackathon.rintis.model.entity.UserRintis;
import hackathon.rintis.scheduler.InsightScheduler;
import hackathon.rintis.service.TransactionService;
import hackathon.rintis.service.UserRintisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private final ExternalApi apiCall;

    @Autowired
    private final TransactionService transactionService;

    @Autowired
    private final InsightScheduler insightScheduler;

    @Autowired
    private final TemplateService templateService;

    @Autowired
    private final UserRintisService userService;

    public TestController(ExternalApi apiCall, TransactionService transactionService, InsightScheduler insightScheduler, TemplateService templateService, UserRintisService userService) {
        this.apiCall = apiCall;
        this.transactionService = transactionService;
        this.insightScheduler = insightScheduler;
        this.templateService = templateService;
        this.userService = userService;
    }

    @GetMapping("/getInsight")
    public List<Map<String, Object>> testScheduling(final Authentication authentication){

        final var user =
                userService.getUserByUsername(authentication.getName());

        System.out.println(user.getId());

        return insightScheduler.getInsightDaily();
    }

    @GetMapping("/getRekomendasiItem")
    public Map<String, Object> getRekomendasiItem(@RequestBody BusinessRequest request){

        Map<String, String> params = new HashMap<>();
        params.put("businessType", request.getBusinessType());
        params.put("budget", request.getBudget());

        String prompt = templateService.generateMessage(1, params);

        KolosalResponse response = apiCall.createChat(prompt);
        ObjectMapper mapper = new ObjectMapper();

        String content = response.getChoices().get(0).getMessage().getContent();

        return mapper.readValue(content, Map.class);
    }

    @GetMapping("/getRekomendasiBisnis")
    public Map<String, Object> getRekomendasiBisnis(@RequestBody BusinessRequest request){

        Map<String, String> params = new HashMap<>();
        params.put("business_model", request.getBusiness_model());
        params.put("budget", request.getBudget());
        params.put("hour", request.getHour());
        params.put("location", request.getLocation());

        String prompt = templateService.generateMessage(2, params);

        KolosalResponse response = apiCall.createChat(prompt);

        ObjectMapper mapper = new ObjectMapper();

        String content = response.getChoices().get(0).getMessage().getContent();

        return mapper.readValue(content, Map.class);
    }

    @GetMapping("/getBalance")
    public Integer getBalance(){
        return transactionService.getCurrentBalance();
    }

    @GetMapping("/getLabaRugi/{periode}")
    public Integer getLabaRugi(@PathVariable LocalDate periode){

        int year = periode.getYear();
        int month = periode.getMonthValue();

        return transactionService.getLabaRugi(year, month);
    }

    @GetMapping("/getAllTransaction/{userId}")
    public List<TransactionList> getAllTransaction(@PathVariable Integer userId){
        return transactionService.getAll(userId);
    }

}
