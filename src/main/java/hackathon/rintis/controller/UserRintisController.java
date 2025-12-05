package hackathon.rintis.controller;

import hackathon.rintis.mapper.RegistrationMapper;
import hackathon.rintis.model.DTO.*;
import hackathon.rintis.service.UserRintisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRintisController {

    private final UserRintisService userRegistrationService;

    private final RegistrationMapper userRegistrationMapper;

    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var registeredUser = userRegistrationService
                .registerUser(userRegistrationMapper.toEntity(registrationDTO));

        return ResponseEntity.ok(
                userRegistrationMapper.toResponseDto(registeredUser)
        );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody final AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(
                userRegistrationService.authenticate(authenticationRequestDto));
    }

    @GetMapping("/info")
    public UserInfoDto userInfo(final Authentication authentication) {
        final var user = userRegistrationService.getUserByUsername(authentication.getName());
        return userRegistrationService.getUserInfo(user.getId());
    }


}
