package hackathon.rintis.repository;

import hackathon.rintis.model.DTO.DataBarResponse;
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
    Double getBalance(@Param("userId") Integer userId);

    @Query(value = """
            SELECT *
            FROM transactions_list
            WHERE id_user = :userId
            ORDER BY date DESC
            LIMIT 3
            """,
            nativeQuery = true)
    List<TransactionList> getRecentTransaction(@Param("userId") int userId);

    @Query(value = """
    SELECT
        COALESCE(SUM(CASE WHEN type = 1 THEN amount ELSE 0 END), 0) -
        COALESCE(SUM(CASE WHEN type = 2 THEN amount ELSE 0 END), 0) AS balance
    FROM transaction_list
    WHERE id_user = :userId
      AND EXTRACT(YEAR FROM date) = :year
      AND EXTRACT(MONTH FROM date) = :month
    """,
            nativeQuery = true)
    Double getLabaRugi(
            @Param("userId") Integer userId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );

    @Query(value = """
    SELECT
        COALESCE(SUM(CASE WHEN type IN (2, 3) THEN amount ELSE 0 END), 0) AS sum_expense,
        COALESCE(SUM(CASE WHEN type = 1 THEN amount ELSE 0 END), 0)      AS sum_income,
        CAST(date AS date)                                              AS tx_date
    FROM transaction_list
    WHERE id_user = :userId
      AND date >= make_date(:year, :month, 1)
      AND date <  make_date(:year, :month, 1) + INTERVAL '1 month'
    GROUP BY CAST(date AS date)
    ORDER BY tx_date
    """,
            nativeQuery = true)
    List<DataBarResponse> getDataBar(
            @Param("userId") Integer userId,
            @Param("year")   Integer year,
            @Param("month")  Integer month
    );

    @Query(
            value = """
            WITH agg AS (
                SELECT 
                    tl."name",
                    SUM(tl.amount) AS total_amount,
                    tl."type",
                    CASE 
                        WHEN tl."type" = '1' THEN 'Revenue'
                        WHEN tl."type" = '2' THEN 'Operational Expense (Opex)'
                        WHEN tl."type" = '3' THEN 'Investment (Capex)'
                        ELSE 'Unknown'
                    END AS type_label
                FROM transaction_list tl
                WHERE tl.id_user = :userId
                  AND tl.date::date = CURRENT_DATE
                GROUP BY tl."name", tl."type"
            )
            SELECT STRING_AGG(
                agg."name" || ' | ' ||
                agg.total_amount || ' | ' ||
                agg."type" || ' | ' ||
                agg.type_label,
                E'\\n'
            ) AS combined_text
            FROM agg
            """,
            nativeQuery = true
    )
    String getDataInsightDaily(@Param("userId") Integer userId);
}
