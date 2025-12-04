package hackathon.rintis.repository;

import hackathon.rintis.model.entity.UserRintis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRintisRepository extends JpaRepository<UserRintis, Integer> {
    Optional<UserRintis> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
