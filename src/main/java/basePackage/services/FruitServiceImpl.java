package basePackage.services;

import basePackage.dao.FruitDao;
import basePackage.entities.Fruit;
import basePackage.exceptions.NullFruitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fruitService")
public class FruitServiceImpl implements FruitService{

    private FruitDao dao;

    @Override
    public List<Fruit> findAll() {
        return dao.findAll();
    }

    @Override
    public Fruit findByName(String name) {
        Fruit f = null;
        try {
            f = dao.findByName(name);
        } catch (NullFruitException e) {
            System.err.println(e.getMessage());
        }
        return f;
    }

    @Override
    public Fruit findById(Long id) {
        Fruit f = null;
        try {
            f = dao.findById(id);
        } catch (NullFruitException e) {
            System.err.println(e.getMessage());
        }
        return f;
    }

    @Override
    public Fruit create(Fruit fruit) {
        Fruit f = null;
        try {
            f = dao.create(fruit);
        } catch (NullFruitException e) {
            System.err.println(e.getMessage());
        }
        return f;
    }

    @Override
    public void update(Fruit fruit) {
        try {
            dao.update(fruit);
        } catch (NullFruitException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Fruit fruit) {
        try {
            dao.delete(fruit);
        } catch (NullFruitException e) {
            System.err.println(e.getMessage());
        }
    }

    public FruitDao getDao() {
        return dao;
    }

    @Autowired
    public void setDao(FruitDao dao) {
        this.dao = dao;
    }
}
