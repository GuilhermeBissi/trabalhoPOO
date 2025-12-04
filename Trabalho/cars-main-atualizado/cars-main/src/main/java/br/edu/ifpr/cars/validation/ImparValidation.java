package br.edu.ifpr.cars.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImparValidation implements ConstraintValidator<Impar, Integer>{

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 2 ==1;
    }


}
