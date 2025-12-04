package hackathon.rintis.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InsertTransDto {

    private String desc;
    private Double amount;
    private LocalDate date;
    private Integer type;
}
