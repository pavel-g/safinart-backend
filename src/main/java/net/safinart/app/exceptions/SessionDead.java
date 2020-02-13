package net.safinart.app.exceptions;

import org.springframework.core.NestedRuntimeException;

public class SessionDead extends NestedRuntimeException {

    public SessionDead(String msg) {
        super(msg);
    }

    /**
     *
     */
    private static final long serialVersionUID = 2705230740056560093L;}