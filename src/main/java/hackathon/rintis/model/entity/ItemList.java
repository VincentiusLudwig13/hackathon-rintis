package hackathon.rintis.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "item_list")
@Data
public class ItemList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String item_name;

    @Column(nullable = false)
    private Double estimated_prices;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String source_of_price_data;

    @JsonIgnore
    @Column(nullable = false)
    private Integer user_id;

}
