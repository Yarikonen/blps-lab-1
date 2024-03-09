package com.itmo.simaland.controller;

import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.dto.warehouse.WarehouseResponse;
import com.itmo.simaland.dto.mapper.WarehouseMapper;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public WarehouseResponse getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.findById(id);
        if (warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
        return warehouseMapper.toResponse(warehouse);
    }

    @PostMapping
    @Operation(summary = "Create a new warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid warehouse data provided", content = @Content)
    })
    public WarehouseResponse createWarehouse(@RequestBody WarehouseRequest request) {
        Warehouse warehouse = warehouseMapper.toEntity(request);
        Warehouse savedWarehouse = warehouseService.save(warehouse);
        return warehouseMapper.toResponse(savedWarehouse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Warehouse not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid warehouse data provided", content = @Content)
    })
    public WarehouseResponse updateWarehouse(@PathVariable Long id, @RequestBody WarehouseRequest request) {
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, request);
        if (updatedWarehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
        return warehouseMapper.toResponse(updatedWarehouse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Warehouse deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Warehouse not found", content = @Content)
    })
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
    }
}
