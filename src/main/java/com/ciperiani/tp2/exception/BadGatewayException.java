package com.ciperiani.tp2.exception;

public class BadGatewayException extends RuntimeException {
    public BadGatewayException(String mensaje) {
        super(mensaje);
    }
}