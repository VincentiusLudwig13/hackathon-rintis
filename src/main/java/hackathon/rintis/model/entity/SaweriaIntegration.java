package hackathon.rintis.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "saweria_integration")
@Data
public class SaweriaIntegration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(unique = true, nullable = false)
    private String webhookToken;

    private String streamKey;
}
