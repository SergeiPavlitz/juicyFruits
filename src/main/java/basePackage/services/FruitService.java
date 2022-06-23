package basePackage.services;

import basePackage.entities.Fruit;
import basePackage.exceptions.EmptyNameException;
import basePackage.exceptions.NoFruitWithIDException;

import java.util.List;

public interface FruitService {

    List<Fruit> findAll();

    Fruit findByName(String name) throws EmptyNameException;

    Fruit findById(Long id) throws NoFruitWithIDException;

    Fruit create(Fruit fruit);

    void update(Fruit fruit);

    void delete(Fruit fruit);

    long countFruits();

}
