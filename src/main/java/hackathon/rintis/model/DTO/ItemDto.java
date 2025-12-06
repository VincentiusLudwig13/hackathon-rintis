package hackathon.rintis.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    @JsonProperty("item name")
    private String itemName;

    private String description;

    @JsonProperty("estimated prices")
    private Double estimatedPrices;

    @JsonProperty("source of price data")
    private String sourceOfPriceData;
}
