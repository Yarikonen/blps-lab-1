package com.itmo.simaland.service;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Page<Warehouse> findAll(PageRequest pageRequest) {
        return warehouseRepository.findAll(pageRequest);
    }

    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    public Warehouse getById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id " + id));
    }

    public Warehouse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse warehouse = getById(id);

        warehouse.setAddress(request.getAddress());

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException("PickUpPoint not found with id: " + id);
        }
        warehouseRepository.deleteById(id);
    }
}
