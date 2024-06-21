package edu.miu.minimarket.exception;

public class BuyerNotFoundException extends RuntimeException {
    public BuyerNotFoundException(String message) {
        super(message);
    }
}
