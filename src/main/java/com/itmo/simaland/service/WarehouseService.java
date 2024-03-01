package com.itmo.simaland.service;

import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }
}
