package com.itmo.simaland.controller;

import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.dto.warehouse.WarehouseResponse;
import com.itmo.simaland.dto.mapper.WarehouseMapper;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Warehouse Controller", description = "Warehouse Controller")
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @GetMapping
    @Operation(summary = "Get all warehouses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    }
    )
    public List<WarehouseResponse> getWarehouses(@Parameter(description = "Pagination request") @Valid PaginationRequest paginationRequest) {
        PageRequest pageRequest = paginationRequest.toPageRequest();
        Page<Warehouse> warehouses = warehouseService.findAll(pageRequest);
        return warehouses.map(warehouseMapper::toResponse).stream().toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a warehouse by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    }
    )
    public WarehouseResponse getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getById(id);
        return warehouseMapper.toResponse(warehouse);
    }

    @PostMapping
    @Operation(summary = "Create a new warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid warehouse data provided", content = @Content)
    })
    public WarehouseResponse createWarehouse(@Valid @RequestBody WarehouseRequest request) {
        Warehouse warehouse = warehouseMapper.toEntity(request);
        warehouse = warehouseService.save(warehouse);
        return warehouseMapper.toResponse(warehouse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Warehouse not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid warehouse data provided", content = @Content)
    })
    public WarehouseResponse updateWarehouse(@PathVariable Long id, @RequestBody @Valid WarehouseRequest request) {
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, request);
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
