package com.itmo.simaland.exception.handler;

import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;



public class AttributeMissingException extends PersistenceException {

    public AttributeMissingException(String message) {
        super(message);
    }
}
