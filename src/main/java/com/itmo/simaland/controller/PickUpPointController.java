package com.itmo.simaland.controller;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.pickUpPoint.PickUpPointResponse;
import com.itmo.simaland.dto.mapper.PickUpPointMapper;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.service.PickUpPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "PickUpPoint Controller", description = "PickUpPoint Controller")
@RequestMapping("/pick-up-points")
@RequiredArgsConstructor
public class PickUpPointController {

    private final PickUpPointService pickUpPointService;
    private final PickUpPointMapper pickUpPointMapper;

    @GetMapping
    @Operation(summary = "Get all pick-up points")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    public Page<PickUpPointResponse> getPickUpPoints(@Parameter(description = "Pagination request") PaginationRequest paginationRequest) {
        PageRequest pageRequest = paginationRequest.toPageRequest();
        Page<PickUpPoint> pickUpPoints = pickUpPointService.findAll(pageRequest);
        return pickUpPoints.map(pickUpPointMapper::toResponse);
    }

    @PostMapping
    @Operation(summary = "Create a new pick-up point")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid pick-up point data provided", content = @Content)
    })
    public PickUpPointResponse createPickUpPoint(@RequestBody PickUpPointRequest request) {
        PickUpPoint pickUpPoint = pickUpPointService.createPickUpPoint(request);
        return pickUpPointMapper.toResponse(pickUpPoint);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a pick-up point")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pick-up point updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pick-up point not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid pick-up point data provided", content = @Content)
    })
    public ResponseEntity<PickUpPointResponse> updatePickUpPoint(@PathVariable Long id, @RequestBody PickUpPointRequest request) {
        PickUpPoint updatedPickUpPoint = pickUpPointService.updatePickUpPoint(id, request);
        return ResponseEntity.ok(pickUpPointMapper.toResponse(updatedPickUpPoint));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pick-up point")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pick-up point deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pick-up point not found", content = @Content)
    })
    public ResponseEntity<Void> deletePickUpPoint(@PathVariable Long id) {
        pickUpPointService.deletePickUpPoint(id);
        return ResponseEntity.noContent().build();
    }
}
