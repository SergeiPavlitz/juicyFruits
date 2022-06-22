package basePackage.controller;

import basePackage.entities.Fruit;
import basePackage.errorResponse.ErrorResponse;
import basePackage.exceptions.EmptyNameException;
import basePackage.exceptions.NoFruitWithIDException;
import basePackage.exceptions.NullFruitException;
import basePackage.services.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/fruit")
public class FruitController {


    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    public static final String TRACE = "trace";

    // TODO: 21.06.2022 добавить нормальную обработку эксепшенов
    private FruitService fruitService;

    // TODO: 19.06.2022 разобраться с получением по имени

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", params = {"name"})
    public Fruit fruitByName(@RequestParam("name") String name) {
        return fruitService.findByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/")
    public Fruit create(@RequestBody Fruit fruit) {
        fruitService.create(fruit);
        return fruit;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public void update(@RequestBody Fruit fruit,
                       @PathVariable Long id) {
        fruitService.update(fruit);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        Fruit fruit = fruitService.findById(id);
        fruitService.delete(fruit);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/count")
    public long countFruits() {
        return fruitService.countFruits();
    }


    @Autowired
    public void setFruitService(FruitService fruitService) {
        this.fruitService = fruitService;
    }


    /*вариант с детальным описанием ошибки*/
    @ExceptionHandler(EmptyNameException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorResponse> handleEmptyNameException(
            EmptyNameException emptyNameException,
            WebRequest request
    ) {
        System.out.println("Fruit name mustn't be empty/null");
        return buildErrorResponse(emptyNameException, emptyNameException.getMessage(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(NoFruitWithIDException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoFruitWithIDException(
            NoFruitWithIDException noFruitWithIDException,
            WebRequest request
    ) {
        System.out.println("Fruit name mustn't be empty/null");
        return buildErrorResponse(noFruitWithIDException, noFruitWithIDException.getMessage(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(NullFruitException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEmptyNameException(
            NullFruitException nullFruitException,
            WebRequest request
    ) {
        System.out.println("Fruit name mustn't be empty/null");
        return buildErrorResponse(nullFruitException, nullFruitException.getMessage(), HttpStatus.NOT_FOUND, request);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);

        if(printStackTrace && isTraceOn(request)){
            errorResponse.setStackTrace(Arrays.toString(exception.getStackTrace()));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(WebRequest request) {
        String [] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    /*простой вариант*/
//    @ExceptionHandler(NoSuchElementException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleNoSuchElementFoundException(
//            NoSuchElementException exception
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(exception.getMessage());
//    }
}
