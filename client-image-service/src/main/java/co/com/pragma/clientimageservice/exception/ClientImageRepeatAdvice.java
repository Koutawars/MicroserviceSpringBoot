package co.com.pragma.clientimageservice.exception;

import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClientImageRepeatAdvice {

    @ResponseBody
    @ExceptionHandler(ClientImageRepeatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Problem clientNotFoundHandler(ClientImageRepeatException ex) {
        return Problem.create()
                .withTitle("Mala petición.")
                .withDetail(ex.getMessage());
    }
}
