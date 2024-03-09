package com.itmo.simaland.controller;

import com.itmo.simaland.dto.installment.InstallmentRequest;
import com.itmo.simaland.dto.installment.InstallmentResponse;
import com.itmo.simaland.service.InstallmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Tag(name = "Installment controller")
@RestController
@RequestMapping("/installments")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @PostMapping("/process")
    @Operation(summary = "Process installment payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Installment processed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstallmentResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Installment processing failed",
                    content = @Content)
    })
    public InstallmentResponse processInstallment(@RequestBody InstallmentRequest request) {
        return installmentService.processInstallment(request);
    }

}
