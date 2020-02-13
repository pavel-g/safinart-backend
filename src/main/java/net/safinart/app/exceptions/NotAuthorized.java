package net.safinart.app.exceptions;

import org.springframework.core.NestedRuntimeException;

public class NotAuthorized extends NestedRuntimeException {

    public NotAuthorized(String msg) {
        super(msg);
    }

    /**
     *
     */
    private static final long serialVersionUID = -3583419073382409759L;}