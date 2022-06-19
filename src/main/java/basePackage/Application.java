package basePackage;

import basePackage.entities.Fruit;
import basePackage.repos.FruitRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "basePackage")
public class Application {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        assert (ctx != null);

        /*тест запроса по имени*/
        FruitRepository fruitRepository = ctx.getBean(FruitRepository.class);
        Fruit f = fruitRepository.findByNameEquals("papaya");
        System.out.println("Fruit found by name " + f);

        System.in.read();
        ctx.close();
    }
}
