package br.com.pedrojaber.exception;

public class MensagemNotFoundException extends RuntimeException{

    public MensagemNotFoundException(String message) {
        super(message);
    }
}
