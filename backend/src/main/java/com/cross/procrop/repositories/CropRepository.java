package com.cross.procrop.repositories;

import com.cross.procrop.models.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByFarmId(Long farmId);
}
