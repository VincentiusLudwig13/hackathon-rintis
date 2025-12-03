package hackathon.rintis.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRequest {
    private String businessType;
    private String budget;
    private String hour;
    private String location;
    private String businessModel;
}
