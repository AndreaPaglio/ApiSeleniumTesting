package net.seniorsoftwareengineer.testing.service;

import java.util.Set;

import javax.validation.ConstraintViolation;

import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Test interface for validate a service
 *
 */
public interface ValidationService {
    public <T> Set<ConstraintViolation<T>> validate(T test) throws TestingException;
}
