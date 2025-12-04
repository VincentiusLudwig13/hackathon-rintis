package hackathon.rintis.service;

import hackathon.rintis.model.DTO.AuthenticationRequestDto;
import hackathon.rintis.model.DTO.AuthenticationResponseDto;
import hackathon.rintis.model.entity.Users;
import hackathon.rintis.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Users registerUser(Users request) {
        if (userRepository.existsByUsername(request.getUsername()) ||
                userRepository.existsByEmail(request.getEmail())) {

            throw new ValidationException(
                    "Username or Email already exists");
        }

        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setEmail(request.getEmail());
        users.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(users);
    }


    public AuthenticationResponseDto authenticate(
            final AuthenticationRequestDto request) {

        final var user = userRepository.findByUsername(request.username()) .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        final var authToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );

//        authenticationManager.authenticate(authToken);

        final var token = jwtService.generateToken(request.username());
        return new AuthenticationResponseDto(token, user.getId());
    }
}
