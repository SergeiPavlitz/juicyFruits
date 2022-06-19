package basePackage.repos;

import basePackage.entities.Fruit;
import org.springframework.data.repository.CrudRepository;

public interface FruitRepository extends CrudRepository<Fruit, Long> {
    Fruit findByNameEquals(String name);
}
