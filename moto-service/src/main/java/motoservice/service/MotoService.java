package motoservice.service;

import lombok.RequiredArgsConstructor;
import motoservice.entities.Moto;
import motoservice.repository.MotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoService {
    private final MotoRepository repository;

    public List<Moto> findAll() {
        return repository.findAll();
    }

    public Moto findById(Long motoId) {
        return repository.findById(motoId)
                         .orElse(null);
    }

    public Moto save(Moto user) {
        return repository.save(user);
    }

    public List<Moto> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
