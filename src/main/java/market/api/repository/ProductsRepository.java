package market.api.repository;

import market.api.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    List<Products> findByName(String name);

}
