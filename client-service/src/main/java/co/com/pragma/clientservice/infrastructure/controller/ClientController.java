package co.com.pragma.clientservice.infrastructure.controller;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.exception.ApiRequestException;
import co.com.pragma.clientservice.domain.usecase.CrudClientUseCase;
import co.com.pragma.clientservice.infrastructure.controller.dto.ClientWithImageDTO;
import co.com.pragma.clientservice.infrastructure.utils.Mapper;
import co.com.pragma.clientservice.infrastructure.controller.exception.ClientCustomException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    private Mapper mapper;
    private CrudClientUseCase clientUseCase;
    private ClientCustomException customException;

    @Autowired
    public ClientController(Mapper mapper, CrudClientUseCase clientUseCase, ClientCustomException customException) {
        this.mapper = mapper;
        this.clientUseCase = clientUseCase;
        this.customException = customException;
    }
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
        List<Client> clients = clientUseCase.getAll();
        return mapper.listClientToListClientWithImageDTO(clients);
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
    ClientWithImageDTO newClient(@RequestBody @Validated ClientWithImageDTO clientWithImageDTO,
                                 BindingResult errors) {
        if(errors.hasErrors()) throw customException.validationException(errors);
        Client client = mapper.clientWithImageDTOToClient(clientWithImageDTO);
        client = clientUseCase.create(client);
        return mapper.clientToClientWithImageDTO(client);
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
        Client client = clientUseCase.read(id);
        return mapper.clientToClientWithImageDTO(client);
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
        Client client = mapper.clientWithImageDTOToClient(clientWithImageDTO);
        client = clientUseCase.update(id, client);
        return mapper.clientToClientWithImageDTO(client);
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
        clientUseCase.delete(id);
    }
}
