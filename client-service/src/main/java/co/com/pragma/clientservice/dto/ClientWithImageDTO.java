package co.com.pragma.clientservice.dto;

import co.com.pragma.clientservice.model.TypeIDNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;

@Getter @Setter
@NoArgsConstructor
@ApiModel
public class ClientWithImageDTO {

    @Schema(description = "Identificador unico del cliente.",
            example = "1")
    private int id;
    @Schema(description = "Nombres del cliente.",
            example = "Karen Alessandra", required = true)
    @NotEmpty(message = "campo no vacío")
    private String names;
    @Schema(description = "Apellidos del cliente.",
            example = "Hernandez Campo", required = true)
    @NotEmpty(message = "campo no vacío")
    private String lastNames;
    @Schema(description = "Edad del cliente.",
            example = "25", required = true)
    @NotNull(message = "campo no nulo")
    @Min(value = 1, message = "tiene que ser minimo que 1")
    private int age;
    @Schema(description = "Ciudad del cliente.",
            example = "Santa Marta", required = true)
    @NotEmpty(message = "campo no vacío")
    private String city;
    @ApiModelProperty
    @Schema(description = "Tipo de identificación del cliente.",
            example = "CC", required = true, type = "TypeIDNumber")
    @NotNull(message = "campo no nulo")
    private TypeIDNumber typeIDNumber;

    @Schema(description = "Numero de identificación.",
            example = "1066326", required = true)
    @NotNull(message = "campo no nulo")
    @Min(value = 1, message = "no puede ser cero")
    private long idNumber;
    @Schema(description = "Imagen de perfil en base64.",
            example = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDIwLjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkNhcGFfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiCgkgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPHN0eWxlIHR5cGU9InRleHQvY3NzIj4KCS5zdDB7ZmlsbDojOUJBQkM5O30KPC9zdHlsZT4KPGc+Cgk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDE2LDM1MmgtMzJjLTM1LjMsMC02NC0yOC43LTY0LTY0di0yMC42YzE0LjItMTYuOCwyNC4zLTM2LjcsMzAuNi01Ny44YzAuNy0zLjUsNC4xLTUuMyw2LjMtNy43CgkJYzEyLjMtMTIuMiwxNC43LTMyLjksNS41LTQ3LjZjLTEuMy0yLjItMy41LTQuMi0zLjQtNi45YzAtMTguOCwwLjEtMzcuNiwwLTU2LjNjLTAuNS0yMi42LTctNDYuMi0yMi44LTYzCgkJQzMyMy40LDE0LjUsMzA1LjgsNi41LDI4Ny42LDNjLTIyLjktNC40LTQ2LjktNC4yLTY5LjcsMS42Yy0xOS43LDUtMzguMiwxNi41LTQ5LjYsMzMuNmMtMTAuMSwxNC45LTE0LjYsMzIuOS0xNS4zLDUwLjcKCQljLTAuMywxOS4xLTAuMSwzOC4yLTAuMSw1Ny40YzAuNCwzLjgtMi44LDYuNC00LjMsOS42Yy04LjcsMTUuNy00LjgsMzcuMSw5LjEsNDguNWMzLjUsMi40LDQuMiw2LjksNS41LDEwLjcKCQljNi4xLDE4LjksMTYuMSwzNi40LDI4LjgsNTEuNlYyODhjMCwzNS4zLTI4LjcsNjQtNjQsNjRIOTZjMCwwLTU4LDE2LTk2LDk2djMyYzAsMTcuNywxNC4zLDMyLDMyLDMyaDQ0OGMxNy43LDAsMzItMTQuMywzMi0zMnYtMzIKCQlDNDc0LDM2OCw0MTYsMzUyLDQxNiwzNTJ6Ii8+CjwvZz4KPC9zdmc+Cg==",
            required = false)
    private String imageBase64;

    public ClientWithImageDTO(int id, String names, String lastNames, int age, String city, TypeIDNumber typeIDNumber, long idNumber, String imageBase64) {
        this.id = id;
        this.names = names;
        this.lastNames = lastNames;
        this.age = age;
        this.city = city;
        this.typeIDNumber = typeIDNumber;
        this.idNumber = idNumber;
        this.imageBase64 = imageBase64;
    }
}
