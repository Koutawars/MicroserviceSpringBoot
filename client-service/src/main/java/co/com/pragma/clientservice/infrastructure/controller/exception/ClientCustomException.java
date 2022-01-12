package co.com.pragma.clientservice.infrastructure.controller.exception;



import co.com.pragma.clientservice.domain.model.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientCustomException {
    public ApiRequestException statusNotFound(int id) {
        String message = "No se pudo encontrar el cliente con el id: " + id;
        return new ApiRequestException(message, HttpStatus.NOT_FOUND);
    }

    public ApiRequestException validationException(BindingResult errors) {
        String message = this.getValidationMessage(errors).toString();
        return new ApiRequestException(message, HttpStatus.BAD_REQUEST);
    }


    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ClientCustomException::getValidationMessage)
                .collect(Collectors.toList());
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            String property = fieldError.getField();
            Object invalidValue = fieldError.getRejectedValue();
            String message = fieldError.getDefaultMessage();
            return String.format("%s %s, pero fue %s", property, message, invalidValue);
        }
        return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
    }
}
