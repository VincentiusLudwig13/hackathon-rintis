package hackathon.rintis.model.DTO;

import java.time.LocalDate;
import java.util.Date;

public record DataBarResponse(Double outcome, Double income, LocalDate date) {
}
