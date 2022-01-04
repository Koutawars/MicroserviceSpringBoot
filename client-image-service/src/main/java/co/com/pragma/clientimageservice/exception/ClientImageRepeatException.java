package co.com.pragma.clientimageservice.exception;

public class ClientImageRepeatException extends RuntimeException {
    public ClientImageRepeatException(Integer clientId, String imageBase64) {
        super("El cliente " + clientId + " ya tiene una imagen.");
    }
}
