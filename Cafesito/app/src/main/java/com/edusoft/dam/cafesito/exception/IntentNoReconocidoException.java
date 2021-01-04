package com.edusoft.dam.cafesito.exception;

/** Todos los Intent de la aplicación se corresponden con Activities y paquetes conocidos.
 *  Si se reciben intent de otros paquetes/apps saltará la excepción
 *
 *
 */
public class IntentNoReconocidoException extends Exception {

    public IntentNoReconocidoException() {
        super();
    }

    public IntentNoReconocidoException(String message) {
        super(message);
    }

    public IntentNoReconocidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntentNoReconocidoException(Throwable cause) {
        super(cause);
    }

    protected IntentNoReconocidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
