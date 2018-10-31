package com.inter.exceptions;

public class EvaluationException extends InterpreterException {
    public EvaluationException(String message) {
        super("EvaluationException: " + message);
    }
}
