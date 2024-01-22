package userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import userservice.entities.User;
import userservice.feignclients.CarFeignClient;
import userservice.feignclients.MotoFeignClient;
import userservice.models.Car;
import userservice.models.Moto;
import userservice.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RestTemplate restTemplate;
    private final CarFeignClient carFeignClient;
    private final MotoFeignClient motoFeignClient;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long userId) {
        return repository.findById(userId)
                         .orElse(null);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public List<Car> findAllCarsByUserId(Long userId) {
        ResponseEntity<List<Car>> responseEntity =
                restTemplate.exchange("http://car-service/cars/user/" + userId,
                                      HttpMethod.GET,
                                      null,
                                      new ParameterizedTypeReference<>() {
                                      });
        return responseEntity.getBody();
    }

    public List<Moto> findAllMotosByUserId(Long userId) {
        ResponseEntity<List<Moto>> responseEntity =
                restTemplate.exchange("http://moto-service/motos/user/" + userId,
                                      HttpMethod.GET,
                                      null,
                                      new ParameterizedTypeReference<>() {
                                      });
        return responseEntity.getBody();
    }

    public Car saveCar(Long userId,
                       Car car) {
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Moto saveMoto(Long userId,
                         Moto moto) {
        moto.setUserId(userId);
        return motoFeignClient.save(moto);
    }

    public Map<String, Object> getUserAndVehicles(Long userId) {
        Map<String, Object> result = new HashMap<>();
        User user = repository.findById(userId)
                              .orElse(null);
        if (user == null) {
            result.put("message",
                       "User doesn't exists");
        }
        result.put("user",
                   user);

        List<Car> cars = carFeignClient.findAllCarsByUserId(userId);
        if (cars.isEmpty()) {
            result.put("cars",
                       "User doesn't have cars");
        } else {
            result.put("cars",
                       cars);
        }
        List<Moto> motos = motoFeignClient.findAllMotosByUserId(userId);
        if (motos.isEmpty()) {
            result.put("motos",
                       "User doesn't have motos");
        } else {
            result.put("motos",
                       motos);
        }
        return result;
    }
}
