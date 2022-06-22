package basePackage.services;

import basePackage.entities.Fruit;
import basePackage.exceptions.EmptyNameException;
import basePackage.exceptions.NoFruitWithIDException;
import basePackage.exceptions.NullFruitException;

import java.util.List;

public interface FruitService {

    List<Fruit> findAll();

    Fruit findByName(String name) throws EmptyNameException;

    Fruit findById(Long id) throws NoFruitWithIDException;

    Fruit create(Fruit fruit) throws NullFruitException;

    void update(Fruit fruit) throws NullFruitException;

    void delete(Fruit fruit);

    long countFruits();

}
