package br.edu.ifpr.cars.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TravelRequestDTO {
    
    @NotNull(message = "O ID do passageiro é obrigatório")
    private Long passengerId;
    
    @NotBlank(message = "A origem é obrigatória")
    private String origin;
    
    @NotBlank(message = "O destino é obrigatório")
    private String destination;
}