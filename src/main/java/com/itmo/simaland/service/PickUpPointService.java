package com.itmo.simaland.service;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.pickUpPoint.UpdatePickUpPointRequest;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.repository.PickUpPointRepository;
import com.itmo.simaland.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PickUpPointService {
    private static final Logger logger = LoggerFactory.getLogger(PickUpPointService.class);


    private final PickUpPointRepository pickUpPointRepository;
    private final WarehouseService warehouseService;

    public Page<PickUpPoint> getAllPickUpPoints(PageRequest pageRequest) {
        return pickUpPointRepository.findAll(pageRequest);
    }

    @Transactional
    public PickUpPoint createPickUpPoint(PickUpPointRequest pickUpPointRequest) {

        PickUpPoint pickUpPoint = new PickUpPoint();
        Warehouse warehouse = warehouseService.getById(pickUpPointRequest.getWarehouseId());

        if (warehouse == null) {
            logger.error("Warehouse is null for id: {}", pickUpPointRequest.getWarehouseId());
        } else {
            logger.info("Warehouse loaded with id: {}", warehouse.getId());
        }

        pickUpPoint.setWarehouse(warehouse);
        pickUpPoint.setAddress(pickUpPointRequest.getAddress());

        try {
            return pickUpPointRepository.save(pickUpPoint);
        } catch (Exception e) {
            logger.error("Error saving PickUpPoint: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<PickUpPoint> findById(Long id) {
        return pickUpPointRepository.findById(id);
    }

    public PickUpPoint getById(Long id) {
        logger.info("trying to find pick up point with id {}", id);
        return findById(id).orElseThrow(() -> new EntityNotFoundException("PickUpPoint not found with id " + id));
    }

    public PickUpPoint updatePickUpPoint(Long id, UpdatePickUpPointRequest request) {
        PickUpPoint pickUpPoint = getById(id);

        if (request.getAddress() != null) {
            pickUpPoint.setAddress(request.getAddress());
        }
        if (request.getWarehouseId() != null) {
            Warehouse warehouse = warehouseService.getById(request.getWarehouseId());
            pickUpPoint.setWarehouse(warehouse);
        }
        return pickUpPointRepository.save(pickUpPoint);
    }

    public void deletePickUpPoint(Long id) {
        if (!pickUpPointRepository.existsById(id)) {
            throw new EntityNotFoundException("PickUpPoint not found with id: " + id);
        }
        pickUpPointRepository.deleteById(id);
    }
}
