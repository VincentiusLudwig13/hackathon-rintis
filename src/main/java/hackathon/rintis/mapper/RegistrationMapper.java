package hackathon.rintis.mapper;

import hackathon.rintis.model.DTO.RegistrationRequestDto;
import hackathon.rintis.model.DTO.RegistrationResponseDto;
import hackathon.rintis.model.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    public Users toEntity(RegistrationRequestDto dto) {
        Users users = new Users();
        users.setUsername(dto.username());
        users.setEmail(dto.email());
        users.setPassword(dto.password()); // raw here, service will encode
        return users;
    }

    public RegistrationResponseDto toResponseDto(Users users) {
        return new RegistrationResponseDto(
                users.getUsername(),
                users.getEmail()
        );
    }
}
