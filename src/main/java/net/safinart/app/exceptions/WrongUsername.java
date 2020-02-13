package net.safinart.app.exceptions;

import org.springframework.core.NestedRuntimeException;

public class WrongUsername extends NestedRuntimeException {

    public WrongUsername(String msg) {
        super(msg);
    }

    /**
     *
     */
    private static final long serialVersionUID = -4334767038853671139L;}