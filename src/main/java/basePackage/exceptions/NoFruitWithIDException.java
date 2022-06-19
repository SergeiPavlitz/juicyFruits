package basePackage.exceptions;


public class NoFruitWithIDException extends NullPointerException {
    private static final String message = "There is no fruit with id ";

    public NoFruitWithIDException(long id) {
        super(message + id);
    }
}
