package basePackage.exceptions;

public class EmptyNameException extends NullPointerException{
    public EmptyNameException() {
        super("Entity name must not be empty/null");
    }
}
