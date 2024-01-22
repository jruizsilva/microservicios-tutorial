package userservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.entities.User;
import userservice.models.Car;
import userservice.models.Moto;
import userservice.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = service.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound()
                                 .build();

        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId) {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(service.save(user));
    }

    @CircuitBreaker(name = "cars-cb",
                    fallbackMethod = "fallbackFindAllCarsByUserId")
    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<Car>> findAllCarsByUserId(@PathVariable("userId") Long userId) {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(service.findAllCarsByUserId(userId));
    }

    @CircuitBreaker(name = "motos-cb",
                    fallbackMethod = "fallbackFindAllMotosByUserId")
    @GetMapping("/{userId}/motos")
    public ResponseEntity<List<Moto>> findAllMotosByUserId(@PathVariable("userId") Long userId) {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(service.findAllMotosByUserId(userId));
    }

    @CircuitBreaker(name = "cars-cb",
                    fallbackMethod = "fallbackSaveCar")
    @PostMapping("/{userId}/cars")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId,
                                       @RequestBody Car car) {
        return ResponseEntity.ok(service.saveCar(userId,
                                                 car));
    }

    @CircuitBreaker(name = "motos-cb",
                    fallbackMethod = "fallbackSaveMoto")
    @PostMapping("/{userId}/motos")
    public ResponseEntity<Moto> saveMoto(@PathVariable("userId") Long userId,
                                         @RequestBody Moto moto) {
        return ResponseEntity.ok(service.saveMoto(userId,
                                                  moto));
    }

    @CircuitBreaker(name = "todos-cb",
                    fallbackMethod = "fallbackGetUserAndVehicles")
    @GetMapping("{userId}/todos")
    public ResponseEntity<Map<String, Object>> getUserAndVehicles(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(service.getUserAndVehicles(userId));
    }

    private ResponseEntity<String> fallbackFindAllCarsByUserId(@PathVariable("userId") Long userId,
                                                               RuntimeException exception) {
        return new ResponseEntity<>("can't get user cars",
                                    HttpStatus.OK);
    }

    private ResponseEntity<String> fallbackSaveCar(@PathVariable("userId") Long userId,
                                                   @RequestBody Car car,
                                                   RuntimeException exception) {
        return new ResponseEntity<>("can't save car",
                                    HttpStatus.OK);
    }

    private ResponseEntity<String> fallbackFindAllMotosByUserId(@PathVariable("userId") Long userId,
                                                                RuntimeException exception) {
        return new ResponseEntity<>("can't get user motos",
                                    HttpStatus.OK);
    }

    private ResponseEntity<String> fallbackSaveMoto(@PathVariable("userId") Long userId,
                                                    @RequestBody Car car,
                                                    RuntimeException exception) {
        return new ResponseEntity<>("can't save moto",
                                    HttpStatus.OK);
    }

    private ResponseEntity<String> fallbackGetUserAndVehicles(@PathVariable("userId") Long userId,
                                                              RuntimeException exception) {
        return new ResponseEntity<>("can't get user vehicles",
                                    HttpStatus.OK);
    }
}
