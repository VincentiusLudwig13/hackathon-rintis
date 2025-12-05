package hackathon.rintis.repository;

import hackathon.rintis.model.DTO.UserInfoDto;
import hackathon.rintis.model.entity.UserRintis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRintisRepository extends JpaRepository<UserRintis, Integer> {
    Optional<UserRintis> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query(
            value = """
            SELECT 
                u.username,
                u.name,
                u.email,
                u.created_at,
                si.webhook_token
            FROM users u
            INNER JOIN saweria_integration si 
                ON si.user_id = u.id
            WHERE u.id = :userId
            """,
            nativeQuery = true
    )
    UserInfoDto getUserInfo(@Param("userId") Integer userId);
}
