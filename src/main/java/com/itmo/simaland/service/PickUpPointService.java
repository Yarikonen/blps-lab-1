package com.itmo.simaland.service;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.repository.PickUpPointRepository;
import com.itmo.simaland.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PickUpPointService {

    private final PickUpPointRepository pickUpPointRepository;
    private final WarehouseRepository warehouseRepository;

    public Page<PickUpPoint> findAll(PageRequest pageRequest) {
        return pickUpPointRepository.findAll(pageRequest);
    }

    public PickUpPoint createPickUpPoint(PickUpPointRequest request) {
        PickUpPoint pickUpPoint = new PickUpPoint();
        pickUpPoint.setAddress(request.getAddress());

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id " + request.getWarehouseId()));
        pickUpPoint.setWarehouse(warehouse);

        return pickUpPointRepository.save(pickUpPoint);
    }

    public Optional<PickUpPoint> findById(Long id) {
        return pickUpPointRepository.findById(id);
    }

    public String findPickUpPointAddressById(Long id) {
        return findById(id).map(PickUpPoint::getAddress)
                .orElseThrow(() -> new EntityNotFoundException("PickUpPoint not found with id " + id));
    }


    public PickUpPoint updatePickUpPoint(Long id, PickUpPointRequest request) {
        PickUpPoint pickUpPoint = pickUpPointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PickUpPoint not found with id: " + id));

        pickUpPoint.setAddress(request.getAddress());

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + request.getWarehouseId()));
        pickUpPoint.setWarehouse(warehouse);

        return pickUpPointRepository.save(pickUpPoint);
    }

    public void deletePickUpPoint(Long id) {
        if (!pickUpPointRepository.existsById(id)) {
            throw new EntityNotFoundException("PickUpPoint not found with id: " + id);
        }
        pickUpPointRepository.deleteById(id);
    }
}
