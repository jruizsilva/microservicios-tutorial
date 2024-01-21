package carservice.service;

import carservice.entities.Car;
import carservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;

    public List<Car> findAll() {
        return repository.findAll();
    }

    public Car findById(Long id) {
        return repository.findById(id)
                         .orElse(null);
    }

    public Car save(Car car) {
        return repository.save(car);
    }

    public List<Car> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
