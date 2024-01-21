package userservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import userservice.models.Moto;

import java.util.List;

@FeignClient(name = "moto-service")
public interface MotoFeignClient {
    @PostMapping("/motos")
    public Moto save(@RequestBody Moto moto);

    @GetMapping("/motos/user/{userId}")
    public List<Moto> findAllMotosByUserId(@PathVariable("userId") Long userId);
}
