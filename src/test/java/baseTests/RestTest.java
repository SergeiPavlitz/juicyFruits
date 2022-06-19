package baseTests;

import basePackage.entities.Fruit;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RestTest {

    final Logger logger = LoggerFactory.getLogger(RestTest.class);
    private static final String URL_GET_ALL_FRUITS = "http://localhost:8080/fruit/listdata";
    private static final String URL_GET_FRUIT_BY_ID = "http://localhost:8080/fruit/{id}";
    private static final String URL_CREATE_FRUIT = "http://localhost:8080/fruit/";
    private static final String URL_UPDATE_FRUIT = "http://localhost:8080/fruit/{id}";
    private static final String URL_DELETE_FRUIT = "http://localhost:8080/fruit/{id}";
    private static final String URL_COUNT_FRUITS = "http://localhost:8080/fruit/count";
    RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testFindAll() {
        logger.info("--> Testing retrieve all fruits");
        Fruit[] fruits = restTemplate.getForObject(URL_GET_ALL_FRUITS, Fruit[].class);
//        assertTrue(fruits.length == 3);
        listFruits(fruits);
        logger.info("--> All fruits : " + fruits.length);
    }

    @Test
    public void testFindById() {
        int id = 11;
        logger.info("--> Testing retrieve a fruit by id : "+ id);
        Fruit fruit = restTemplate.getForObject(URL_GET_FRUIT_BY_ID, Fruit.class, id);
        assertNotNull(fruit);
        logger.info(fruit.toString());
    }

    @Test
    public void testUpdate() {
        int id = 11;
        logger.info("--> Testing update fruit by id : " + id);
        Fruit fruit = restTemplate.getForObject(URL_UPDATE_FRUIT, Fruit.class, id);
        fruit.setColor("lightgreen");
        restTemplate.put(URL_UPDATE_FRUIT, fruit, id);
        logger.info("fruit update successfully: " + fruit);
    }

    @Test
    public void testDelete() {
        int idForDelete = 12;
        logger.info("--> Testing delete Fruit by id : " + idForDelete);
        restTemplate.delete(URL_DELETE_FRUIT, idForDelete);
        Fruit[] fruits = restTemplate.getForObject(URL_GET_ALL_FRUITS, Fruit[].class);
        Boolean found = false;
        for (Fruit s : fruits) {
            if (s.getId() == idForDelete) {
                found = true;
            }
        }
        assertFalse(found);
        listFruits(fruits);
    }

    @Test
    public void testCreate() {
        logger.info("--> Testing create fruit");
        Fruit fruitNew = new Fruit();
        fruitNew.setName("lemon");
        fruitNew.setColor("yellow");
        fruitNew.setHasStone(true);
        fruitNew = restTemplate.postForObject(URL_CREATE_FRUIT, fruitNew, Fruit.class);
        logger.info("Fruit created successfully: " + fruitNew);
        logger.info("Check created Fruit in all query: ");
        Fruit[] fruits = restTemplate.getForObject(URL_GET_ALL_FRUITS, Fruit[].class);
        listFruits(fruits);
    }

    @Test
    public void testCount(){
        logger.info("--> Testing count fruits");
        Long n = restTemplate.getForObject(URL_COUNT_FRUITS, Long.class);
        logger.info("Fruit count is done successfully. There are " + n + " fruits.");
    }

    private void listFruits(Fruit[] fruits) {
        Arrays.stream(fruits).forEach(s -> logger.info(s.toString()));
    }
    
}
