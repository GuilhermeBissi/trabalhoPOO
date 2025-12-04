package br.edu.ifpr.cars.validation; // ✅ CORRIGIR

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImparValidation implements ConstraintValidator<Impar, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // ✅ Adicionar verificação de null
        }
        return value % 2 == 1;
    }
}