package edu.miu.minimarket.exception;

public class SellerNotApprovedException extends RuntimeException {
    public SellerNotApprovedException(String message) {
        super(message);
    }
}