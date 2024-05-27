package com.github.mauricioaniche.ck.exceptions;

public class ClassLevelMetricsLoadingException extends RuntimeException {
    public ClassLevelMetricsLoadingException(Throwable cause) {
        super("Something is really wrong", cause);
    }
}