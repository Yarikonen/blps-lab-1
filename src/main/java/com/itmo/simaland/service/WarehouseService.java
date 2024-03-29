package com.itmo.simaland.service;

import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.dto.item.ItemWithQuantityResponse;
import com.itmo.simaland.dto.warehouse.WarehouseItemResponse;
import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public WarehouseItemResponse getWarehouseItems(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + warehouseId));

        List<ItemWithQuantityResponse> itemResponses = warehouse.getWarehouseItems().stream().map(warehouseItem -> {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setId(warehouseItem.getItem().getId());
            itemResponse.setName(warehouseItem.getItem().getName());
            itemResponse.setPrice(warehouseItem.getItem().getPrice());

            ItemWithQuantityResponse itemWithQuantityResponse = new ItemWithQuantityResponse();
            itemWithQuantityResponse.setItem(itemResponse);
            itemWithQuantityResponse.setQuantity(warehouseItem.getQuantity());

            return itemWithQuantityResponse;
        }).collect(Collectors.toList());

        WarehouseItemResponse warehouseItemResponse = new WarehouseItemResponse();
        warehouseItemResponse.setWarehouseId(warehouse.getId());
        warehouseItemResponse.setWarehouseAddress(warehouse.getAddress());
        warehouseItemResponse.setItems(itemResponses);

        return warehouseItemResponse;
    }
}
