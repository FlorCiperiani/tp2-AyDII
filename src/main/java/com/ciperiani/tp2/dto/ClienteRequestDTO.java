package com.ciperiani.tp2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @Schema(description = "Nombre del cliente (mínimo 2 caracteres)", example = "Ana")
    @NotBlank(message = "no puede estar vacío")
    @Size(min = 2, message = "debe tener al menos 2 caracteres")
    private String nombre;

    @Schema(description = "Apellido del cliente (mínimo 2 caracteres)", example = "Garcia")
    @NotBlank(message = "no puede estar vacío")
    @Size(min = 2, message = "debe tener al menos 2 caracteres")
    private String apellido;

    @Schema(description = "Correo electrónico único y válido", example = "ana.garcia@mail.com")
    @NotBlank(message = "no puede estar vacío")
    @Email(message = "debe ser un email válido")
    private String email;

    @Schema(description = "Teléfono opcional (solo dígitos)", example = "3814567890")
    @Pattern(regexp = "^\\d*$", message = "solo debe contener dígitos")
    private String telefono;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}