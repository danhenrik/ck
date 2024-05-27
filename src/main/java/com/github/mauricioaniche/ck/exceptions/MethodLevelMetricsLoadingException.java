package com.github.mauricioaniche.ck.exceptions;

public class MethodLevelMetricsLoadingException extends RuntimeException {
    public MethodLevelMetricsLoadingException(Throwable cause) {
        super("Something is really wrong", cause);
    }
}