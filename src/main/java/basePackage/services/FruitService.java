package basePackage.services;

import basePackage.entities.Fruit;

import java.util.List;

public interface FruitService {

    List<Fruit> findAll();

    Fruit findByName(String name);

    Fruit findById(Long id);

    Fruit create(Fruit fruit);

    void update(Fruit fruit);

    void delete(Fruit fruit);

}
