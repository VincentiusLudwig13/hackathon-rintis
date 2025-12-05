package hackathon.rintis.repository;

import hackathon.rintis.model.entity.SaweriaIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaweriaIntegrationRepo
        extends JpaRepository<SaweriaIntegration, Integer> {
    Optional<SaweriaIntegration> findByWebhookToken(String webhookToken);
}
