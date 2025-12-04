package hackathon.rintis.mapper;

import hackathon.rintis.model.DTO.RegistrationRequestDto;
import hackathon.rintis.model.DTO.RegistrationResponseDto;
import hackathon.rintis.model.entity.UserRintis;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    public UserRintis toEntity(RegistrationRequestDto dto) {
        UserRintis userRintis = new UserRintis();
        userRintis.setUsername(dto.username());
        userRintis.setEmail(dto.email());
        userRintis.setPassword(dto.password()); // raw here, service will encode
        return userRintis;
    }

    public RegistrationResponseDto toResponseDto(UserRintis userRintis) {
        return new RegistrationResponseDto(
                userRintis.getUsername(),
                userRintis.getEmail()
        );
    }
}
