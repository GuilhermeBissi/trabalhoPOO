package br.edu.ifpr.cars.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifpr.cars.domain.*;
import br.edu.ifpr.cars.dto.TravelRequestDTO;

@Service
public class TravelService {
    
    @Autowired
    private TravelRequestRepository travelRequestRepository;
    
    @Autowired
    private PassengerRepository passengerRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    /**
     * Passageiro cria uma solicitação de viagem
     */
    public TravelRequest createTravelRequest(TravelRequestDTO dto) {
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Passageiro não encontrado"
            ));
        
        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setPassenger(passenger);
        travelRequest.setOrigin(dto.getOrigin());
        travelRequest.setDestination(dto.getDestination());
        travelRequest.setStatus(TravelRequestStatus.CREATED);
        
        return travelRequestRepository.save(travelRequest);
    }
    
    /**
     * Motorista aceita uma viagem
     * Regras:
     * - Só pode aceitar viagens com status CREATED
     * - Se já estiver ACCEPTED ou FINISHED → retornar 400
     * - Se id não existir → retornar 404
     */
    public TravelRequest acceptTravel(Long travelId, Long driverId) {
        TravelRequest travel = travelRequestRepository.findById(travelId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Viagem não encontrada"
            ));
        
        Driver driver = driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Motorista não encontrado"
            ));
        
        if (travel.getStatus() == TravelRequestStatus.ACCEPTED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Esta viagem já foi aceita"
            );
        }
        
        if (travel.getStatus() == TravelRequestStatus.FINISHED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Esta viagem já foi finalizada"
            );
        }
        
        if (travel.getStatus() != TravelRequestStatus.CREATED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Não é possível aceitar viagem em status: " + travel.getStatus()
            );
        }
        
        travel.setDriver(driver);
        travel.setStatus(TravelRequestStatus.ACCEPTED);
        travel.setAcceptanceDate(LocalDateTime.now());
        
        return travelRequestRepository.save(travel);
    }
    
    /**
     * Motorista recusa uma viagem
     */
    public TravelRequest refuseTravel(Long travelId, Long driverId) {
        TravelRequest travel = travelRequestRepository.findById(travelId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Viagem não encontrada"
            ));
        
        driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Motorista não encontrado"
            ));
        
        if (travel.getStatus() != TravelRequestStatus.CREATED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Não é possível recusar viagem em status: " + travel.getStatus()
            );
        }
        
        travel.setStatus(TravelRequestStatus.REFUSED);
        
        return travelRequestRepository.save(travel);
    }
    
    /**
     * Finalizar uma viagem
     */
    public TravelRequest finishTravel(Long travelId) {
        TravelRequest travel = travelRequestRepository.findById(travelId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Viagem não encontrada"
            ));
        
        if (travel.getStatus() != TravelRequestStatus.ACCEPTED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Apenas viagens aceitas podem ser finalizadas"
            );
        }
        
        travel.setStatus(TravelRequestStatus.FINISHED);
        
        return travelRequestRepository.save(travel);
    }
    
    /**
     * Listar todas as viagens
     */
    public List<TravelRequest> listAllTravels() {
        return travelRequestRepository.findAll();
    }
    
    /**
     * Buscar viagem por ID
     */
    public TravelRequest findTravelById(Long id) {
        return travelRequestRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Viagem não encontrada"
            ));
    }
    
    /**
     * Listar viagens por passageiro
     */
    public List<TravelRequest> listTravelsByPassenger(Long passengerId) {
        return travelRequestRepository.findByPassengerId(passengerId);
    }
    
    /**
     * Listar viagens por motorista
     */
    public List<TravelRequest> listTravelsByDriver(Long driverId) {
        return travelRequestRepository.findByDriverId(driverId);
    }
    
    /**
     * Listar viagens por status
     */
    public List<TravelRequest> listTravelsByStatus(TravelRequestStatus status) {
        return travelRequestRepository.findByStatus(status);
    }
}