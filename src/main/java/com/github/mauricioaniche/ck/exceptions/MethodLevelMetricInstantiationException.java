package com.github.mauricioaniche.ck.exceptions;

public class MethodLevelMetricInstantiationException extends RuntimeException {
    public MethodLevelMetricInstantiationException(Throwable cause) {
        super("Something is really wrong", cause);
    }
}