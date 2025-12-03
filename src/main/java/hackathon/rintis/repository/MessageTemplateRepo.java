package hackathon.rintis.repository;

import hackathon.rintis.model.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepo extends JpaRepository<MessageTemplate, Integer> {
}
