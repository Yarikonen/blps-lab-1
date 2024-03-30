package com.itmo.simaland.repository;

import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.model.entity.WarehouseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Long> {
    Optional<WarehouseItem> findByItemId(Long itemId);
}
