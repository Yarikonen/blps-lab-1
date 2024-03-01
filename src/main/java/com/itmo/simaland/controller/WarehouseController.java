package com.itmo.simaland.controller;

import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.dto.warehouse.WarehouseResponse;
import com.itmo.simaland.dto.mapper.WarehouseMapper;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Warehouse Controller", description = "Warehouse Controller")
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get a warehouse by its ID")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.findById(id);
        if (warehouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        WarehouseResponse warehouseResponse = warehouseMapper.toResponse(warehouse);
        return new ResponseEntity<>(warehouseResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new warehouse")
    @ApiResponse(responseCode = "201", description = "Created")
    public WarehouseResponse createWarehouse(@RequestBody WarehouseRequest request) {
        Warehouse warehouse = warehouseMapper.toEntity(request);
        Warehouse savedWarehouse = warehouseService.save(warehouse);
        return warehouseMapper.toResponse(savedWarehouse);
    }
}
