package hackathon.rintis.service;

import hackathon.rintis.model.DTO.IntegrationExpenseDto;
import hackathon.rintis.model.DTO.ItemDto;
import hackathon.rintis.model.DTO.UpdateItemListDto;
import hackathon.rintis.model.entity.ItemList;
import hackathon.rintis.model.entity.TransactionList;
import hackathon.rintis.repository.ItemListRepository;
import hackathon.rintis.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemListService {

    private final ItemListRepository itemListRepository;
    private final TransactionRepo transactionRepo;

    public void insertItem(List<ItemDto> items,Integer userId){

        for (ItemDto data : items){
            ItemList item = new ItemList();
            item.setItem_name(data.getItemName());
            item.setDescription(data.getDescription());
            item.setEstimated_prices(data.getEstimatedPrices());
            item.setSource_of_price_data(data.getSourceOfPriceData());
            item.setUser_id(userId);

            itemListRepository.save(item);
        }

    }

    public List<ItemList> getListItem(Integer userId) {
        return itemListRepository.findAll()
                .stream()
                .filter(v -> userId.equals(v.getUser_id()) && v.getIsAdded() == null)
                .collect(Collectors.toList());
    }

    public void IntegrationToExpense(List<IntegrationExpenseDto> request, Integer userId){
        request.forEach(dto -> {

            ItemList item = itemListRepository.findById(dto.itemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            item.setIsAdded("2");

            itemListRepository.save(item);

            TransactionList trx = new TransactionList();
            trx.setName(item.getItem_name());
            trx.setDate(java.sql.Date.valueOf(LocalDate.now()));
            trx.setAmount(item.getEstimated_prices());
            trx.setId_user(userId);
            trx.setType(3);

            transactionRepo.save(trx);

        });
    }

    public void upsertItem(UpdateItemListDto request){

        ItemList item;

        if (request.itemId() != null) {
            item = itemListRepository.findById(request.itemId())
                    .orElse(new ItemList());
        } else {
            item = new ItemList();
        }

        item.setEstimated_prices(request.estimatedPrice());
        item.setItem_name(request.itemName());
        item.setDescription(request.description());
        item.setSource_of_price_data(request.source_of_price_data());
        item.setIsAdded(request.isAdded());

        itemListRepository.save(item);

    }

    public void deleteItem(List<UpdateItemListDto> request){

        request.forEach(r -> {
            ItemList item = itemListRepository.findById(r.itemId())
                    .orElse(new ItemList());

            item.setIsAdded("3");

            itemListRepository.save(item);
        });

    }

}
