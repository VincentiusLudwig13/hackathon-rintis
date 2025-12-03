package hackathon.rintis.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prompt_list")
@Data
public class MessageTemplate {
    @Id
    String id;

    @Column(nullable = false)
    String text_prompt;
}
