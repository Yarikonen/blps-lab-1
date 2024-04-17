package com.itmo.simaland.repository;

import com.itmo.simaland.model.entity.PickUpPoint;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickUpPointRepository extends JpaRepository<PickUpPoint, Long> {
}
