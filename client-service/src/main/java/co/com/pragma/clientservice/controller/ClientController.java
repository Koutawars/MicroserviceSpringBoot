package co.com.pragma.clientservice.controller;

import co.com.pragma.clientservice.dto.ClientWithImageDTO;
import co.com.pragma.clientservice.exception.ApiRequestException;
import co.com.pragma.clientservice.exception.ClientCustomException;
import co.com.pragma.clientservice.model.TypeIDNumber;
import co.com.pragma.clientservice.service.ClientService;
import com.google.inject.BindingAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientCustomException customException;

    @Operation(
            summary = "El servicio consigue todos los clientes",
            method = "GET",
            tags = "get",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operación exitosa",
                            content = @Content(schema = @Schema(
                                    implementation = ClientWithImageDTO.class)))})
    @GetMapping
    List<ClientWithImageDTO> getClients() {
        //@RequestParam(required = false) Long idNumber, @RequestParam(required = false) TypeIDNumber typeIDNumber
        // System.out.println("idNumber: " + idNumber + " typeIDNumber: " + typeIDNumber);
        return clientService.getClients();
    }

    @Operation(
            summary = "El servicio permite el guardado de datos de un cliente",
            method = "POST",
            tags = "save",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operación exitosa",
                            content = @Content(schema = @Schema(
                                    implementation = ClientWithImageDTO.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "El cliente envió datos incorrectos.",
                            content = @Content(schema = @Schema(implementation = ApiRequestException.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error en el proceso.",
                            content = @Content(schema = @Schema(implementation = Exception.class)))})
    @PostMapping
    ClientWithImageDTO newClient(@RequestBody @Validated ClientWithImageDTO client, BindingResult errors) {
        if(errors.hasErrors()) throw customException.validationException(errors);
        return clientService.addClient(client);
    }

    @Operation(
            summary = "El servicio consigue un cliente.",
            method = "GET",
            tags = "get",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operación exitosa",
                            content = @Content(schema = @Schema(
                                    implementation = ClientWithImageDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontro el usuario a buscar.",
                            content = @Content(schema = @Schema(implementation = ApiRequestException.class)))})
    @GetMapping("{id}")
    ClientWithImageDTO getClient(@PathVariable int id) {
        return clientService.getClient(id);
    }

    @Operation(
            summary = "El servicio actualiza un cliente.",
            method = "PUT",
            tags = "update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operación exitosa",
                            content = @Content(schema = @Schema(
                                    implementation = ClientWithImageDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontro el usuario a reemplazar.",
                            content = @Content(schema = @Schema(implementation = ApiRequestException.class)))})
    @PutMapping("{id}")
    ClientWithImageDTO replaceClient(@PathVariable("id") int id,
                                     @RequestBody @Validated ClientWithImageDTO clientWithImageDTO,
                                     BindingResult errors){
        if(errors.hasErrors()) throw customException.validationException(errors);
        return clientService.replaceClient(clientWithImageDTO, id);
    }

    @Operation(
            summary = "El servicio borra un cliente.",
            method = "DELETE",
            tags = "delete",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operación exitosa",
                            content = @Content(schema = @Schema(
                                    implementation = ClientWithImageDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontro el usuario a borrar.",
                            content = @Content(schema = @Schema(implementation = ApiRequestException.class)))})
    @DeleteMapping("{id}")
    void deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
    }
}
