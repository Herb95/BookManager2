package com.graywolfz.bms.exception;

/**
 * 库存不足异常
 * 也就是 图书已经借走
 */
public class NotEnoughException extends RuntimeException {
    public NotEnoughException(String message) {
        super(message);
    }
}
