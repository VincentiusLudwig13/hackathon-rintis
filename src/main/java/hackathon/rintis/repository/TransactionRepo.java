package hackathon.rintis.repository;

import hackathon.rintis.model.entity.TransactionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepo extends JpaRepository<TransactionList, Integer> {

    @Query(value = """
            SELECT
                COALESCE(SUM(CASE WHEN type = '1' THEN amount ELSE 0 END), 0) -
                COALESCE(SUM(CASE WHEN type IN ('2', '3') THEN amount ELSE 0 END), 0) AS balance
            FROM transaction_list
            WHERE id_user = :userId;
        """,
            nativeQuery = true)
    Integer getBalance(@Param("userId") Integer userId);

    @Query(value = """
    SELECT
        COALESCE(SUM(CASE WHEN type = '1' THEN amount ELSE 0 END), 0) -
        COALESCE(SUM(CASE WHEN type = '2' THEN amount ELSE 0 END), 0) AS balance
    FROM transaction_list
    WHERE id_user = :userId
      AND YEAR(`date`) = :year
      AND MONTH(`date`) = :month
    """,
            nativeQuery = true)
    Integer getLabaRugi(
            @Param("userId") Integer userId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );
}
