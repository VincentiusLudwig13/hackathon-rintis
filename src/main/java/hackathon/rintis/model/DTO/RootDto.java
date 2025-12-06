package hackathon.rintis.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RootDto {

    private DataDto data;

    @JsonProperty("response_code")
    private int responseCode;
}
