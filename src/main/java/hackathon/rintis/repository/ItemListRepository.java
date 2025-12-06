package hackathon.rintis.repository;

import hackathon.rintis.model.entity.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemListRepository extends JpaRepository<ItemList, Integer> {
}
