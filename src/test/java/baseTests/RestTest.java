package baseTests;

import basePackage.entities.Fruit;
import basePackage.exceptions.EmptyNameException;
import basePackage.exceptions.NoFruitWithIDException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RestTest {

    final Logger logger = LoggerFactory.getLogger(RestTest.class);
    private static final String URL_GET_ALL_FRUITS = "http://localhost:8080/fruit/listdata";
    private static final String URL_GET_FRUIT_BY_ID = "http://localhost:8080/fruit/{id}";
    private static final String URL_GET_FRUIT_BY_NAME = "http://localhost:8080/fruit/?name=";
    private static final String URL_CREATE_FRUIT = "http://localhost:8080/fruit/";
    private static final String URL_UPDATE_FRUIT = "http://localhost:8080/fruit/{id}";
    private static final String URL_DELETE_FRUIT_BY_ID = "http://localhost:8080/fruit/{id}";
    private static final String URL_COUNT_FRUITS = "http://localhost:8080/fruit/count";
    RestTemplate restTemplate;

    private static Long count = 0L;
    private static String nameForCreateAndDelete = "ssss";

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testCount(){
        logger.info("--> Testing count fruits");
        count = restTemplate.getForObject(URL_COUNT_FRUITS, Long.class);
        logger.info("Fruit count is done successfully. There are " + count + " fruits.");
    }

    @Test
    public void testFindAll() {
        logger.info("--> Testing retrieve all fruits");
        Fruit[] fruits = restTemplate.getForObject(URL_GET_ALL_FRUITS, Fruit[].class);
        assertNotNull(fruits);
        assertEquals(fruits.length, (long) count);
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
    public void testFindByName() {
        String name = "papaya";
        logger.info("--> Testing retrieve a fruit by name : "+ name);
        Fruit fruit = restTemplate.getForObject(URL_GET_FRUIT_BY_NAME + name, Fruit.class);
        assertNotNull(fruit);
        logger.info(fruit.toString());
    }

    @Test
    public void testUpdate() {
        int id = 11;
        logger.info("--> Testing update fruit by id : " + id);
        Fruit fruit = restTemplate.getForObject(URL_UPDATE_FRUIT, Fruit.class, id);
        assertNotNull(fruit);
        fruit.setColor("lightgreen");
        restTemplate.put(URL_UPDATE_FRUIT, fruit, id);
        logger.info("fruit update successfully: " + fruit);
    }

    @Test
    public void testCreate() {
        logger.info("--> Testing create fruit");
        Fruit fruitNew = new Fruit();
        fruitNew.setName(nameForCreateAndDelete);
        fruitNew.setColor("yyyy");
        fruitNew.setHasStone(true);
        fruitNew = restTemplate.postForObject(URL_CREATE_FRUIT, fruitNew, Fruit.class);
        assertNotNull(fruitNew);
        logger.info("Fruit created successfully: " + fruitNew);
        Fruit fruit = restTemplate.getForObject(URL_GET_FRUIT_BY_NAME + nameForCreateAndDelete, Fruit.class);
        assertNotNull(fruit);
        logger.info("Created fruit : " + fruit);
    }

    @Test
    public void testDeleteById() {
        Fruit fruit = restTemplate.getForObject(URL_GET_FRUIT_BY_NAME + nameForCreateAndDelete, Fruit.class);
        assertNotNull(fruit);
        Long idForDelete = fruit.getId();
        logger.info("--> Testing delete Fruit by id : " + idForDelete);
        restTemplate.delete(URL_DELETE_FRUIT_BY_ID, idForDelete);
        Fruit[] fruits = restTemplate.getForObject(URL_GET_ALL_FRUITS, Fruit[].class);
        assertNotNull(fruits);
        boolean found = false;
        for (Fruit s : fruits) {
            if (s.getId().equals(idForDelete)) {
                found = true;
                break;
            }
        }
        assertFalse(found);
        listFruits(fruits);
    }



    @Test
    public void EmptyNameExceptionCatch(){
        String name = "";
        logger.info("--> Testing retrieve a fruit by emptyName");
        Fruit fruit = null;
        EmptyNameException emptyNameException = new EmptyNameException();
        try {
            fruit = restTemplate.getForObject(URL_GET_FRUIT_BY_NAME + name, Fruit.class);
        } catch (RestClientException e) {
            HttpClientErrorException h = (HttpClientErrorException)e;
            assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), h.getRawStatusCode());
            assertNotNull(h.getMessage());
            assertTrue(h.getMessage().contains(emptyNameException.getMessage()));
        }
    }

    @Test
    public void noFruitWithIDExceptionCatch(){
        long id = 10000;
        logger.info("--> Testing retrieve a fruit by no exist id");
        NoFruitWithIDException noFruitWithIDException = new NoFruitWithIDException(id);
        try {
            Fruit fruit = restTemplate.getForObject(URL_GET_FRUIT_BY_ID , Fruit.class, id);
        } catch (RestClientException e) {
            HttpClientErrorException h = (HttpClientErrorException)e;
            assertEquals(HttpStatus.NOT_FOUND.value(), h.getRawStatusCode());
            assertNotNull(h.getMessage());
            assertTrue(h.getMessage().contains(noFruitWithIDException.getMessage()));
        }
    }

    private void listFruits(Fruit[] fruits) {
        Arrays.stream(fruits).forEach(s -> logger.info(s.toString()));
    }
    
}
