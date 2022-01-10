package co.com.pragma.clientservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumerador
 * Tipos de documentos <br>
 * {@link TypeIDNumber#CC}: Cedula de ciudadanía <br>
 * {@link TypeIDNumber#TI}: Tarjeta de identidad <br>
 * {@link TypeIDNumber#CE}: Cedula de extranjería <br>
 * {@link TypeIDNumber#NIP}: Numero de identificación personal <br>
 * {@link TypeIDNumber#NIT}: Numero de identificación tributaria <br>
 * {@link TypeIDNumber#PAP}: Pasaporte
 */
@ApiModel
public enum TypeIDNumber {
    @Schema(description = "Cedula de ciudadanía")
    CC,
    @Schema(description = "Tarjeta de identidad")
    TI,
    @Schema(description = "Cedula de extranjería")
    CE,
    @Schema(description = "Numero de identificación personal")
    NIP,
    @Schema(description = "Numero de identificación tributaria")
    NIT,
    @Schema(description = "Pasaporte")
    PAP
}