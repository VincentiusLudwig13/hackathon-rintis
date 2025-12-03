package hackathon.rintis.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "transaction_list")
@Data
public class TransactionList {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Date date;

    @JsonIgnore
    @Column(nullable = false)
    private Integer id_user;

}
