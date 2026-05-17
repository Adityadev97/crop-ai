package com.cross.procrop.repositories;

import com.cross.procrop.models.DiseaseScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseScanRepository extends JpaRepository<DiseaseScan, Long> {
    List<DiseaseScan> findByCropId(Long cropId);
}
