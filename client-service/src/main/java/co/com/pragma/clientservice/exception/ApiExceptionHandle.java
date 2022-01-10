package co.com.pragma.clientservice.exception;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandle {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleClientRequestException(ApiRequestException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Class<?> type = e.getRequiredType();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message;
        if(type.isEnum()){
            message = "El parametro " + e.getName() + " debe tener un valor entre: " + StringUtils.join(type.getEnumConstants(), ", ");
        }
        else{
            message = "El parametro " + e.getName() + " debe de ser tipo " + type.getTypeName();
        }

        ApiException apiException = new ApiException(
                message,
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
