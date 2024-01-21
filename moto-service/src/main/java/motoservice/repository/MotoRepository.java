package motoservice.repository;

import motoservice.entities.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    @Query("select m from Moto m where m.userId = :userId")
    List<Moto> findAllByUserId(@Param("userId") Long userId);
}