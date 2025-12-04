package br.edu.ifpr.cars.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "travel_requests")
public class TravelRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "O passageiro é obrigatório")
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;
    
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    
    @NotBlank(message = "A origem é obrigatória")
    @Column(nullable = false)
    private String origin;
    
    @NotBlank(message = "O destino é obrigatório")
    @Column(nullable = false)
    private String destination;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelRequestStatus status;
    
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    
    @Column(name = "acceptance_date")
    private LocalDateTime acceptanceDate;
    
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        if (this.status == null) {
            this.status = TravelRequestStatus.CREATED;
        }
    }
}