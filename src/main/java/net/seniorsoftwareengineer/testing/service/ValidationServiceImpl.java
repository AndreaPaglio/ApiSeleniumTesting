package net.seniorsoftwareengineer.testing.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Validation implementation for validate input
 *
 */
@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {
    ValidatorFactory factory;

    public ValidationServiceImpl() {
	super();
	this.factory = Validation.buildDefaultValidatorFactory();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validate(T test) throws TestingException {
	Validator validator = factory.getValidator();
	Set<ConstraintViolation<T>> errors = validator.validate(test);
	if (errors.size() > 0) {
	    throw new TestingException(errors.stream().findFirst().get().getMessage(), "validation");
	}
	return errors;
    }
}
