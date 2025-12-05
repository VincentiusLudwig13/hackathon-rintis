package hackathon.rintis.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class SaweriaWebhookPayload {

    private String version;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    private String id;
    private String type;

    @JsonProperty("amount_raw")
    private Double amountRaw;

    private Integer cut;

    @JsonProperty("donator_name")
    private String donatorName;

    @JsonProperty("donator_email")
    private String donatorEmail;

    @JsonProperty("donator_is_user")
    private Boolean donatorIsUser;

    private String message;
}
