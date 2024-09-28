package zhedron.shop.exceptions;

public class EmailExistException extends Exception {
    public EmailExistException (String message) {
        super(message);
    }
}
