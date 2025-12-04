package br.edu.ifpr.cars.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpr.cars.domain.TravelRequest;
import br.edu.ifpr.cars.domain.TravelRequestStatus;
import br.edu.ifpr.cars.dto.TravelRequestDTO;
import br.edu.ifpr.cars.service.TravelService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/travels", produces = MediaType.APPLICATION_JSON_VALUE)
public class TravelController {
    
    @Autowired
    private TravelService travelService;
    
    /**
     * POST /api/travels - Passageiro cria uma solicitação de viagem
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TravelRequest createTravel(@RequestBody @Valid TravelRequestDTO dto) {
        return travelService.createTravelRequest(dto);
    }
    
    /**
     * PATCH /api/travels/{id}/accept - Motorista aceita uma viagem
     */
    @PatchMapping("/{id}/accept")
    public TravelRequest acceptTravel(
            @PathVariable Long id, 
            @RequestParam Long driverId) {
        return travelService.acceptTravel(id, driverId);
    }
    
    /**
     * PATCH /api/travels/{id}/refuse - Motorista recusa uma viagem
     */
    @PatchMapping("/{id}/refuse")
    public TravelRequest refuseTravel(
            @PathVariable Long id, 
            @RequestParam Long driverId) {
        return travelService.refuseTravel(id, driverId);
    }
    
    /**
     * PATCH /api/travels/{id}/finish - Finalizar uma viagem
     */
    @PatchMapping("/{id}/finish")
    public TravelRequest finishTravel(@PathVariable Long id) {
        return travelService.finishTravel(id);
    }
    
    /**
     * GET /api/travels - Listar todas as viagens
     */
    @GetMapping
    public List<TravelRequest> listAllTravels() {
        return travelService.listAllTravels();
    }
    
    /**
     * GET /api/travels/{id} - Buscar viagem por ID
     */
    @GetMapping("/{id}")
    public TravelRequest getTravelById(@PathVariable Long id) {
        return travelService.findTravelById(id);
    }
    
    /**
     * GET /api/travels/passenger/{passengerId} - Listar viagens por passageiro
     */
    @GetMapping("/passenger/{passengerId}")
    public List<TravelRequest> getTravelsByPassenger(@PathVariable Long passengerId) {
        return travelService.listTravelsByPassenger(passengerId);
    }
    
    /**
     * GET /api/travels/driver/{driverId} - Listar viagens por motorista
     */
    @GetMapping("/driver/{driverId}")
    public List<TravelRequest> getTravelsByDriver(@PathVariable Long driverId) {
        return travelService.listTravelsByDriver(driverId);
    }
    
    /**
     * GET /api/travels/status/{status} - Listar viagens por status
     */
    @GetMapping("/status/{status}")
    public List<TravelRequest> getTravelsByStatus(@PathVariable TravelRequestStatus status) {
        return travelService.listTravelsByStatus(status);
    }
}