package hackathon.rintis.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequestDto(
        @NotBlank String username,
        @Email @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password
) {}
