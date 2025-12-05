package hackathon.rintis.controller;

import hackathon.rintis.mapper.RegistrationMapper;
import hackathon.rintis.model.DTO.*;
import hackathon.rintis.model.entity.SaweriaIntegration;
import hackathon.rintis.repository.SaweriaIntegrationRepo;
import hackathon.rintis.service.UserRintisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRintisController {

    private final UserRintisService userRegistrationService;

    private final SaweriaIntegrationRepo saweriaIntegrationRepo;

    private final RegistrationMapper userRegistrationMapper;

    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var registeredUser = userRegistrationService
                .registerUser(userRegistrationMapper.toEntity(registrationDTO));

        RegisterTokenSaweria(registeredUser.getId());

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

        System.out.println(user.getId());

        return userRegistrationService.getUserInfo(user.getId());
    }

    public void RegisterTokenSaweria(Integer userId){
        SaweriaIntegration saweriaUser = new SaweriaIntegration();
        saweriaUser.setUserId(userId);
        saweriaUser.setWebhookToken(CodeGenerator.generate("sirfetch"));

        saweriaIntegrationRepo.save(saweriaUser);
    }

    public class CodeGenerator {

        private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        private static final SecureRandom RANDOM = new SecureRandom();

        public static String generate(String username) {
            StringBuilder sb = new StringBuilder(5);
            for (int i = 0; i < 5; i++) {
                int index = RANDOM.nextInt(UPPER.length());
                sb.append(UPPER.charAt(index));
            }
            return username + sb;
        }
    }


}
