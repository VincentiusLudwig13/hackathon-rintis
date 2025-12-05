package hackathon.rintis.model.DTO;

import java.time.LocalDate;

public record UserInfoDto(String username, String email, LocalDate registration_date, String token_saweria) {
}
