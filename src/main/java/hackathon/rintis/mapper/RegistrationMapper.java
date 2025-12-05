package hackathon.rintis.mapper;

import hackathon.rintis.model.DTO.RegistrationRequestDto;
import hackathon.rintis.model.DTO.RegistrationResponseDto;
import hackathon.rintis.model.entity.UserRintis;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class RegistrationMapper {

    public UserRintis toEntity(RegistrationRequestDto dto) {
        UserRintis userRintis = new UserRintis();
        userRintis.setUsername(dto.username());
        userRintis.setName(dto.name());
        userRintis.setEmail(dto.email());
        userRintis.setCreatedAt(Date.valueOf(LocalDate.now()));
        userRintis.setPassword(dto.password()); // raw here, service will encode
        return userRintis;
    }

    public RegistrationResponseDto toResponseDto(UserRintis userRintis) {
        return new RegistrationResponseDto(
                userRintis.getUsername(),
                userRintis.getEmail(),
                userRintis.getName()
        );
    }
}
