package com.itmo.simaland.service;

import com.itmo.simaland.dto.item.ItemWithQuantityResponse;
import com.itmo.simaland.dto.warehouse.WarehouseItemResponse;
import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.model.entity.WarehouseItem;
import com.itmo.simaland.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void addItemsToWarehouse(Long id, Item item, int quantity) {
        Warehouse warehouse = getById(id);
        WarehouseItem warehouseItem= new WarehouseItem();
        warehouseItem.setItem(item);
        warehouseItem.setWarehouse(warehouse);
        warehouseItem.setQuantity(quantity);

        List<WarehouseItem> items = warehouse.getWarehouseItems();
        items.add(warehouseItem);
        warehouse.setWarehouseItems(items);
        warehouseRepository.save(warehouse);
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

    @Transactional
    public WarehouseItemResponse getWarehouseItems(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + warehouseId));

        List<ItemWithQuantityResponse> itemResponses = warehouse.getWarehouseItems().stream().map(warehouseItem -> {
            ItemWithQuantityResponse itemWithQuantityResponse = new ItemWithQuantityResponse();
            itemWithQuantityResponse.setId(warehouseItem.getItem().getId());
            itemWithQuantityResponse.setName(warehouseItem.getItem().getName());
            itemWithQuantityResponse.setPrice(warehouseItem.getItem().getPrice());
            itemWithQuantityResponse.setQuantity(warehouseItem.getQuantity());

            return itemWithQuantityResponse;
        }).collect(Collectors.toList());

        WarehouseItemResponse warehouseItemResponse = new WarehouseItemResponse();
        warehouseItemResponse.setWarehouseId(warehouse.getId());
        warehouseItemResponse.setWarehouseAddress(warehouse.getAddress());
        warehouseItemResponse.setItems(itemResponses);

        return warehouseItemResponse;
    }

    @Transactional
    public int getItemQuantityById(Long itemId) {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getWarehouseItems().stream())
                .filter(warehouseItem -> warehouseItem.getItem().getId().equals(itemId))
                .mapToInt(WarehouseItem::getQuantity)
                .sum();
    }
}
