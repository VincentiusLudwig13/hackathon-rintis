package hackathon.rintis.service;

import hackathon.rintis.model.DTO.AuthenticationRequestDto;
import hackathon.rintis.model.DTO.AuthenticationResponseDto;
import hackathon.rintis.model.entity.UserRintis;
import hackathon.rintis.repository.UserRintisRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.GONE;

@Service
@RequiredArgsConstructor
public class UserRintisService {

    private final UserRintisRepository userRintisRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRintis registerUser(UserRintis request) {
        // Check if username OR email already exists
        if (userRintisRepository.existsByUsername(request.getUsername())
                || userRintisRepository.existsByEmail(request.getEmail())) {

            throw new ValidationException("Username or Email already exists");
        }

        UserRintis userRintis = new UserRintis();
        userRintis.setUsername(request.getUsername());
        userRintis.setEmail(request.getEmail());
        userRintis.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRintisRepository.save(userRintis);
    }

    public UserRintis getUserByUsername(final String username) {
        return userRintisRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(GONE,
                        "The user account has been deleted or inactivated"));
    }

    public AuthenticationResponseDto authenticate(final AuthenticationRequestDto request) {

        UserRintis user = userRintisRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("Uhuy");

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                );

        System.out.println("brengky");

        authenticationManager.authenticate(authToken);

        System.out.println("Awyeah");

        String token = jwtService.generateToken(request.username());

        return new AuthenticationResponseDto(token, user.getId());
    }
}

