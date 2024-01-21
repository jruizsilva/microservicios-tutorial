package userservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import userservice.models.Car;

import java.util.List;

@FeignClient(name = "car-service")
public interface CarFeignClient {

    @PostMapping("/cars")
    Car save(@RequestBody Car car);

    @GetMapping("/cars/user/{userId}")
    List<Car> findAllCarsByUserId(@PathVariable("userId") Long userId);
}
