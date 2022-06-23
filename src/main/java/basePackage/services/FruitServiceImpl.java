package basePackage.services;

import basePackage.entities.Fruit;
import basePackage.exceptions.EmptyNameException;
import basePackage.exceptions.NoFruitWithIDException;
import basePackage.repos.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("fruitService")
public class FruitServiceImpl implements FruitService{

    private FruitRepository fruitRepository;

    @Override
    public List<Fruit> findAll() {

        List<Fruit> result = new ArrayList<>();
        Iterable<Fruit> iterable = fruitRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    @Override
    public Fruit findByName(String name) throws EmptyNameException {
        if (name == null || name.isBlank()){
            throw new EmptyNameException();
        }
        return fruitRepository.findByNameEquals(name);
    }

    @Override
    public Fruit findById(Long id) throws NoFruitWithIDException {
        Optional<Fruit> optional = fruitRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else{
            throw new NoFruitWithIDException(id);
        }
    }

    @Override
    public Fruit create(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    @Override
    public void update(Fruit fruit) {
        fruitRepository.save(fruit);
    }

    @Override
    public void delete(Fruit fruit) {
        fruitRepository.delete(fruit);
    }

    @Override
    public long countFruits() {
        return fruitRepository.count();
    }

    @Autowired
    public void setFruitRepository(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

}
