package com.itmo.simaland.controller;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.pickUpPoint.PickUpPointResponse;
import com.itmo.simaland.dto.mapper.PickUpPointMapper;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.service.PickUpPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "PickUpPoint Controller", description = "PickUpPoint Controller")
@RequestMapping("/pick-up-points")
@RequiredArgsConstructor
public class PickUpPointController {

    private final PickUpPointService pickUpPointService;
    private final PickUpPointMapper pickUpPointMapper;

    @GetMapping("/list")
    @Operation(summary = "Get all pick-up points")
    @ApiResponse(responseCode = "200", description = "OK")
    public Page<PickUpPointResponse> getPickUpPoints(@Parameter(description = "Pagination request") PaginationRequest paginationRequest) {
        PageRequest pageRequest = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize());
        Page<PickUpPoint> pickUpPoints = pickUpPointService.findAll(pageRequest);
        return pickUpPoints.map(pickUpPointMapper::toResponse);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new pick-up point")
    @ApiResponse(responseCode = "201", description = "Created")
    public PickUpPointResponse createPickUpPoint(@RequestBody PickUpPointRequest request) {
        PickUpPoint pickUpPoint = pickUpPointService.createPickUpPoint(request);
        return pickUpPointMapper.toResponse(pickUpPoint);
    }
}
