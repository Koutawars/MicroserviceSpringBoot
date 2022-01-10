package co.com.pragma.clientservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiRequestException extends RuntimeException {
    private HttpStatus httpStatus;
    public ApiRequestException(String message) {
        super(message);
    }
    public ApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
