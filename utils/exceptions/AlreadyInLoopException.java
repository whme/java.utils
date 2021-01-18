package utils.exceptions;

@SuppressWarnings("serial")
public class AlreadyInLoopException extends RuntimeException{
    public AlreadyInLoopException(String errorMessage) {
        super(errorMessage);
    }
}
