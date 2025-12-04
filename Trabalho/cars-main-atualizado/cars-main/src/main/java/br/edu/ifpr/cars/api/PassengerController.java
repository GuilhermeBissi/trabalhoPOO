package br.edu.ifpr.cars.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifpr.cars.domain.Passenger;
import br.edu.ifpr.cars.domain.PassengerRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/passengers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerController {
    
    @Autowired
    private PassengerRepository passengerRepository;
    
    @GetMapping
    public List<Passenger> listPassengers() {
        return passengerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return passengerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Passageiro não encontrado"
            ));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Passenger createPassenger(@RequestBody @Valid Passenger passenger) {
        return passengerRepository.save(passenger);
    }
    
    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody @Valid Passenger passenger) {
        Passenger found = getPassenger(id);
        found.setName(passenger.getName());
        found.setEmail(passenger.getEmail());
        return passengerRepository.save(found);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassenger(@PathVariable Long id) {
        passengerRepository.deleteById(id);
    }
}