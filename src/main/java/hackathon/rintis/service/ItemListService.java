package hackathon.rintis.service;

import hackathon.rintis.model.DTO.ItemDto;
import hackathon.rintis.model.entity.ItemList;
import hackathon.rintis.repository.ItemListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemListService {

    private final ItemListRepository itemListRepository;

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

}
