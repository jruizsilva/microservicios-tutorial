package userservice.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<Car>> findAllCarsByUserId(@PathVariable("userId") Long userId) {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(service.findAllCarsByUserId(userId));
    }

    @GetMapping("/{userId}/motos")
    public ResponseEntity<List<Moto>> findAllMotosByUserId(@PathVariable("userId") Long userId) {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(service.findAllMotosByUserId(userId));
    }

    @PostMapping("/{userId}/cars")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId,
                                       @RequestBody Car car) {
        return ResponseEntity.ok(service.saveCar(userId,
                                                 car));
    }

    @PostMapping("/{userId}/motos")
    public ResponseEntity<Moto> saveMoto(@PathVariable("userId") Long userId,
                                         @RequestBody Moto moto) {
        return ResponseEntity.ok(service.saveMoto(userId,
                                                  moto));
    }

    @GetMapping("{userId}/todos")
    public ResponseEntity<Map<String, Object>> getUserAndVehicles(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(service.getUserAndVehicles(userId));
    }
}
