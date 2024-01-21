package carservice.controller;

import carservice.entities.Car;
import carservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService service;

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        List<Car> cars = service.findAll();
        if (cars.isEmpty()) {
            return ResponseEntity.notFound()
                                 .build();

        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id) {
        Car car = service.findById(id);
        if (car == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car car) {
        return ResponseEntity.ok(service.save(car));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> findAllByUserId(@PathVariable("userId") Long userId) {
        List<Car> cars = service.findAllByUserId(userId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent()
                                 .build();
        }
        return ResponseEntity.ok(cars);
    }
}
