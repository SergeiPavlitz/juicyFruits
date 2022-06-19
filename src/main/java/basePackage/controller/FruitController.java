package basePackage.controller;

import basePackage.entities.Fruit;
import basePackage.services.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fruit")
public class FruitController {

    private FruitService fruitService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/listdata")
    public List<Fruit> listData() {
        return fruitService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Fruit findFruitById(@PathVariable Long id) {
        return fruitService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/")
    public Fruit create(@RequestBody Fruit fruit) {
        fruitService.create(fruit);
        return fruit;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value="/{id}")
    public void update(@RequestBody Fruit singer,
                       @PathVariable Long id) {
        fruitService.update(singer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id) {
        Fruit fruit = fruitService.findById(id);
        fruitService.delete(fruit);
    }


    public FruitService getFruitService() {
        return fruitService;
    }

    @Autowired
    public void setFruitService(FruitService fruitService) {
        this.fruitService = fruitService;
    }

}
