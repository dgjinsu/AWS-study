package linklibrary.exception;

public class JsonParseException extends RuntimeException{

    public JsonParseException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
