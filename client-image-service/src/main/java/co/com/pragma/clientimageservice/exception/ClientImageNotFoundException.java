package co.com.pragma.clientimageservice.exception;

public class ClientImageNotFoundException extends RuntimeException {
    public ClientImageNotFoundException(int id) {
        super("No se ha podido encontrar la imagen del cliente: " + id);
    }
}
