package br.edu.ifpr.cars.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlacaValidaValidator implements ConstraintValidator<PlacaValida, String> {

    @Override
    public void initialize(PlacaValida constraintAnnotation) {
        // Inicialização se necessário
    }

    @Override
    public boolean isValid(String placa, ConstraintValidatorContext context) {
        // Aceita null (use @NotNull/@NotBlank separadamente se quiser obrigar preenchimento)
        if (placa == null || placa.isEmpty()) {
            return true;
        }

        // Remove espaços em branco
        placa = placa.trim().toUpperCase();

        // Padrão Mercosul: ABC1D23 (3 letras + 1 número + 1 letra + 2 números)
        String regexMercosul = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$";
        
        // Padrão antigo (opcional): ABC1234 (3 letras + 4 números)
        String regexAntigo = "^[A-Z]{3}[0-9]{4}$";

        // Aceita tanto o padrão Mercosul quanto o antigo
        return placa.matches(regexMercosul) || placa.matches(regexAntigo);
    }
}