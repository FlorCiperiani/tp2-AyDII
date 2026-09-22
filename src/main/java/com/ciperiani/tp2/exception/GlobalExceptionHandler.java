package com.ciperiani.tp2.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura errores cuando falla @Valid en objetos individuales o cuerpos JSON directos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        List<String> errores = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String mensaje = error.getDefaultMessage();
            if (error instanceof FieldError) {
                String campo = ((FieldError) error).getField();
                String objeto = error.getObjectName();
                errores.add("Elemento/Campo '" + objeto + "." + campo + "': " + mensaje);
            } else {
                errores.add(mensaje);
            }
        });

        ApiResponse<List<String>> respuesta = new ApiResponse<>(
                400,
                "Error de validación",
                errores
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    // Captura errores de validación cuando se usan listas
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<List<String>>> manejarHandlerMethodValidation(
            HandlerMethodValidationException ex) {

        List<String> errores = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {
            errores.add(error.getDefaultMessage());
        });

        ApiResponse<List<String>> respuesta = new ApiResponse<>(
                400,
                "Los datos o parámetros enviados no son válidos",
                errores
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    // Captura violaciones de restricciones directas
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<List<String>>> manejarConstraintViolation(
            ConstraintViolationException ex) {

        List<String> errores = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> {
            errores.add(violation.getPropertyPath() + ": " + violation.getMessage());
        });

        ApiResponse<List<String>> respuesta = new ApiResponse<>(
                400,
                "Parámetros inválidos",
                errores
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> manejarArgumentoInvalido(
            IllegalArgumentException ex) {

        ApiResponse<Void> respuesta = new ApiResponse<>(
                400,
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarErrorInterno(
            Exception ex) {
        
        
        ex.printStackTrace();

        ApiResponse<Void> respuesta = new ApiResponse<>(
                500,
                "Ocurrió un error interno en el servidor",
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(respuesta);
    }

    //Ejercicio 2
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException ex) {

        ApiResponse<Void> respuesta = new ApiResponse<>(
                404,
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(respuesta);
    }

    //Ejercicio 3
    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ApiResponse<Void>> manejarBadGateway(BadGatewayException ex) {
        ApiResponse<Void> respuesta = new ApiResponse<>(502, ex.getMessage(), null);
        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_GATEWAY).body(respuesta);
    }

    //Ejercicio 4

    // Captura el error de email duplicado devolviendo data: null
    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ApiResponse<Void>> manejarEmailDuplicado(
            EmailDuplicadoException ex) {

        ApiResponse<Void> respuesta = new ApiResponse<>(
                400,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

        // Ejercicio 3/6 — validaciones de negocio de divisas
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> manejarBadRequest(BadRequestException ex) {
        ApiResponse<Void> respuesta = new ApiResponse<>(400, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }
}