package hackathon.rintis.controller;

import hackathon.rintis.externalAPI.ExternalApi;
import hackathon.rintis.helper.TemplateService;
import hackathon.rintis.model.DTO.*;
import hackathon.rintis.model.entity.TransactionList;
import hackathon.rintis.scheduler.InsightScheduler;
import hackathon.rintis.service.ItemListService;
import hackathon.rintis.service.TransactionService;
import hackathon.rintis.service.UserRintisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

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

    @Autowired
    private final ItemListService itemListService;

    public TransactionController(ExternalApi apiCall, TransactionService transactionService, InsightScheduler insightScheduler, TemplateService templateService, UserRintisService userService, ItemListService itemListService) {
        this.apiCall = apiCall;
        this.transactionService = transactionService;
        this.insightScheduler = insightScheduler;
        this.templateService = templateService;
        this.userService = userService;
        this.itemListService = itemListService;
    }

    @GetMapping("/getInsight")
    public List<Map<String, Object>> testScheduling(final Authentication authentication){

        final var user = userService.getUserByUsername(authentication.getName());
        return insightScheduler.getInsightDaily(user.getId());
    }

    @PostMapping("/getRekomendasiItem")
    public Map<String, Object> getRekomendasiItem(@RequestBody ItemRequest request, final Authentication authentication){

        final var user = userService.getUserByUsername(authentication.getName());

        Map<String, String> params = new HashMap<>();
        params.put("businessType", request.getBusinessType());
        params.put("budget", request.getBudget());

        String prompt = templateService.generateMessage(1, params);

        KolosalResponse response = apiCall.createChat(prompt);
        ObjectMapper mapper = new ObjectMapper();

        String content = response.getChoices().get(0).getMessage().getContent();

        RootDto response_item = mapper.readValue(content, RootDto.class);
        List<ItemDto> items = response_item.getData().getItems();

        itemListService.insertItem(items, user.getId());

        return mapper.readValue(content, Map.class);
    }

    @PostMapping("/getRekomendasiBisnis")
    public Map<String, Object> getRekomendasiBisnis(@RequestBody BusinessRequest request, final Authentication authentication){

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
    public Double getBalance(final Authentication authentication){
        final var user = userService.getUserByUsername(authentication.getName());
        return transactionService.getCurrentBalance(user.getId());
    }

    @GetMapping("/getLabaRugi/{periode}")
    public Double getLabaRugi(@PathVariable LocalDate periode, final Authentication authentication){

        final var user = userService.getUserByUsername(authentication.getName());
        int year = periode.getYear();
        int month = periode.getMonthValue();

        return transactionService.getLabaRugi(year, month, user.getId());
    }

    @GetMapping("/getRecentTransaction")
    public List<TransactionList> getAllTransaction(final Authentication authentication){
        final var user = userService.getUserByUsername(authentication.getName());
        return transactionService.getRecentCashflow(user.getId());
    }

    @GetMapping("/getDataBar")
    public List<DataBarResponse> getDataBar(final Authentication authentication){

        LocalDate now = LocalDate.now();

        final var user = userService.getUserByUsername(authentication.getName());
        return transactionService.getDataBar(user.getId(), now.getYear(), now.getMonthValue());
    }

    @GetMapping("/getAll")
    public List<TransactionList> getAll(final Authentication authentication){

        LocalDate now = LocalDate.now();

        final var user = userService.getUserByUsername(authentication.getName());
        return transactionService.getAll(user.getId());
    }

    @PostMapping("/insertTransaksi")
    public String insertTransaksi(@RequestBody List<InsertTransDto> request, final Authentication authentication){

        final var user = userService.getUserByUsername(authentication.getName());
        transactionService.inserTransaksi(request, user.getId());

        return "Berhasil";
    }

    @PostMapping("/upsertDataItem")
    public String upsertDataItem(@RequestBody UpdateItemListDto request){
        itemListService.upsertItem(request);
        return "OK";
    }

    @GetMapping("/getItemList")
    public String getItemList(final Authentication authentication){

        final var user = userService.getUserByUsername(authentication.getName());
        itemListService.getListItem(user.getId());
        return "OK";
    }



    @PostMapping("/integrationExpense")
    public String integrationExpense( List<IntegrationExpenseDto> request, final Authentication authentication){

        final var user = userService.getUserByUsername(authentication.getName());
        itemListService.IntegrationToExpense(request, user.getId());

        return "OK";
    }

}
