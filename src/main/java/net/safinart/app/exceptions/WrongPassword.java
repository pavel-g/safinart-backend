package net.safinart.app.exceptions;

import org.springframework.core.NestedRuntimeException;

public class WrongPassword extends NestedRuntimeException {

    public WrongPassword(String msg) {
        super(msg);
    }

    /**
     *
     */
    private static final long serialVersionUID = 462799407501346167L;}