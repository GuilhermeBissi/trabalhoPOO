package br.edu.ifpr.cars.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRequestRepository extends JpaRepository<TravelRequest, Long> {
    List<TravelRequest> findByPassengerId(Long passengerId);
    List<TravelRequest> findByDriverId(Long driverId);
    List<TravelRequest> findByStatus(TravelRequestStatus status);
}