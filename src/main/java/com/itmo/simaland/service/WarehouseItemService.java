package com.itmo.simaland.service;

import com.itmo.simaland.dto.warehouse.WarehouseItemRequest;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.model.entity.WarehouseItem;
import com.itmo.simaland.repository.WarehouseItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseItemService {
    private final WarehouseItemRepository warehouseItemRepository;
    private final ItemService itemService;
    private final WarehouseService warehouseService;

    public Optional<WarehouseItem> findByItemId(Long itemId) {
        return warehouseItemRepository.findByItemId(itemId);
    }

    public void addItemToWarehouse(Long warehouseId, WarehouseItemRequest request) {
        Warehouse warehouse = warehouseService.getById(warehouseId);
        Item item = itemService.getItemById(request.getItemId());

        Optional<WarehouseItem> existingWarehouseItem = warehouse.getWarehouseItems().stream()
                .filter(warehouseItem -> warehouseItem.getItem().equals(item))
                .findFirst();

        if (existingWarehouseItem.isPresent()) {
            WarehouseItem warehouseItem = existingWarehouseItem.get();
            warehouseItem.setQuantity(warehouseItem.getQuantity() + request.getQuantity());
            warehouseItemRepository.save(warehouseItem);
        } else {
            WarehouseItem warehouseItem = new WarehouseItem();
            warehouseItem.setWarehouse(warehouse);
            warehouseItem.setItem(item);
            warehouseItem.setQuantity(request.getQuantity());
            warehouseItemRepository.save(warehouseItem);
        }
    }

    public void updateWarehouseItem(WarehouseItem warehouseItem) {
        warehouseItemRepository.save(warehouseItem);
    }

    @Transactional
    public void reserveItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Item storedItem = orderItem.getItem();
            WarehouseItem warehouseItem = findByItemId(storedItem.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse item not found for item id: " + storedItem.getId()));
            if (warehouseItem.getQuantity() < orderItem.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for item id: " + storedItem.getId());
            }
            warehouseItem.setQuantity(warehouseItem.getQuantity() - orderItem.getQuantity());
            updateWarehouseItem(warehouseItem);
        }
    }
}
