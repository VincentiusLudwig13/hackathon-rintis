package hackathon.rintis.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "transaction_list")
@Data
public class TransactionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private Date date;

    @JsonIgnore
    @Column(nullable = false)
    private Integer id_user;

}
