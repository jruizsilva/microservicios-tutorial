package motoservice.controller;

import lombok.RequiredArgsConstructor;
import motoservice.entities.Moto;
import motoservice.service.MotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motos")
public class MotoController {
    private final MotoService service;

    @GetMapping
    public ResponseEntity<List<Moto>> findAll() {
        List<Moto> motos = service.findAll();
        if (motos.isEmpty()) {
            return ResponseEntity.notFound()
                                 .build();

        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{motoId}")
    public ResponseEntity<Moto> findById(@PathVariable Long motoId) {
        Moto moto = service.findById(motoId);
        if (moto == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> save(@RequestBody Moto moto) {
        return ResponseEntity.ok(service.save(moto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Moto>> findAllByUserId(@PathVariable("userId") Long userId) {
        List<Moto> motos = service.findAllByUserId(userId);
        if (motos.isEmpty()) {
            return ResponseEntity.noContent()
                                 .build();
        }
        return ResponseEntity.ok(motos);
    }
}
