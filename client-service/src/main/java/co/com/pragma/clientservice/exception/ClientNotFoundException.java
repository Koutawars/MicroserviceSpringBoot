package co.com.pragma.clientservice.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(int id) {
        super("No se ha podido encontrar el empleado: " + id);
    }
}
