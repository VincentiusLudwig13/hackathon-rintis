package hackathon.rintis.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class InsertTransDto {

    private String desc;
    private Double amount;
    private Date date;
    private Integer type;
}
