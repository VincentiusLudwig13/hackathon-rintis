package hackathon.rintis.model.DTO;

public record UpdateItemListDto(Integer itemId, String description, String itemName, String source_of_price_data, Double estimatedPrice, String isAdded) {
}