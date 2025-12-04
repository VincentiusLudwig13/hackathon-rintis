package hackathon.rintis.model.DTO;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
