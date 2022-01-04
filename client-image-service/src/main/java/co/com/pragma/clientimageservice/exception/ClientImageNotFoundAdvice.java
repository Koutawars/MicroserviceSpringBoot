package co.com.pragma.clientimageservice.exception;

import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClientImageNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ClientImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Problem clientNotFoundHandler(ClientImageNotFoundException ex) {
        return Problem.create()
                .withTitle("Recurso no encontrado.")
                .withDetail(ex.getMessage());
    }
}
