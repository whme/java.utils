package utils.exceptions;

@SuppressWarnings("serial")
public class NotInLoopException extends RuntimeException{
    public NotInLoopException(String errorMessage) {
        super(errorMessage);
    }
}
