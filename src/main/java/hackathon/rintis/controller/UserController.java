package hackathon.rintis.controller;

import hackathon.rintis.mapper.RegistrationMapper;
import hackathon.rintis.model.DTO.AuthenticationRequestDto;
import hackathon.rintis.model.DTO.AuthenticationResponseDto;
import hackathon.rintis.model.DTO.RegistrationRequestDto;
import hackathon.rintis.model.DTO.RegistrationResponseDto;
import hackathon.rintis.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userRegistrationService;

    private final RegistrationMapper userRegistrationMapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var registeredUser = userRegistrationService
                .registerUser(userRegistrationMapper.toEntity(registrationDTO));

        return ResponseEntity.ok(
                userRegistrationMapper.toResponseDto(registeredUser)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody final AuthenticationRequestDto authenticationRequestDto
    ) {
        return ResponseEntity.ok(
                userRegistrationService.authenticate(authenticationRequestDto));
    }
}
