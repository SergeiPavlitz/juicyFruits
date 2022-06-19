package basePackage.exceptions;

public class NullFruitException extends NullPointerException{

    public NullFruitException() {
        super("Fruit object must not be null");
    }
}
