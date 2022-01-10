package co.com.pragma.clientservice.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


import java.time.ZonedDateTime;


@ApiModel
@Getter @Setter
@AllArgsConstructor
public class ApiException {
    @Schema(description = "Mensaje del error.",
            example = "No se ha encontrado cliente x")
    private final String message;
    @Schema(description = "http status",
            example = "NOT_FOUND")
    private final HttpStatus httpStatus;
    @Schema(description = "Tiempo")
    private final ZonedDateTime zonedDateTime;

}
