package com.itmo.simaland.service;

import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }
}
